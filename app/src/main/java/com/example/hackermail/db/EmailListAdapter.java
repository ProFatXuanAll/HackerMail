package com.example.hackermail.db;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.hackermail.EditDataActivity;
import com.example.hackermail.MainActivity;
import com.example.hackermail.R;
import com.example.hackermail.SendMailAlarmReceiver;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

import static android.support.v4.content.ContextCompat.getSystemService;

public class EmailListAdapter extends RecyclerView.Adapter<EmailListAdapter.EmailViewHolder> {

    private final LayoutInflater layoutInflater;
    private List<Email> emails;
    private static ClickListener clickListener;

    private static Context context;
    public EmailListAdapter(Context _context) {
        this.layoutInflater = LayoutInflater.from(_context);
        this.context = _context;
    }

    @Override
    public EmailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = this.layoutInflater.inflate(R.layout.email_recyclerview_item, parent, false);
        return new EmailViewHolder(itemView);
    }


    public void AlarmService_on(final Email current){
        if (current.getClock() <  Calendar.getInstance().getTimeInMillis() )
            return ;
        final MainActivity main = (MainActivity)context;

        TemplateViewModel templateViewModel = ViewModelProviders.of( main ).get(TemplateViewModel.class);
        templateViewModel.getAllTemplates(current.getTopicid()).observe( main  , new Observer<List<Template>>() {
            @Override
            public void onChanged(@Nullable List<Template> templates) {
                final String[] all_templates = new String[templates.size()];
                for (int i = 0; i < templates.size(); i++)
                    all_templates[i] = templates.get(i).getTemplate();
                Random random1 = new Random(1);
                final Intent emailIntent = new Intent(main, SendMailAlarmReceiver.class);
                emailIntent.putExtra(EditDataActivity.EXTRA_DATA_MAIL_TO ,  current.getTo());
                emailIntent.putExtra(EditDataActivity.EXTRA_DATA_MAIL_SUBJECT , current.getSubject());


                if (templates.size() != 0 ) {
                    Log.d("message-text", all_templates[Math.abs(random1.nextInt()) % templates.size()]);
                    //emailIntent.putExtra(EditDataActivity.EXTRA_DATA_MAIL_BODY , " test  mail send");
                    emailIntent.putExtra(EditDataActivity.EXTRA_DATA_MAIL_BODY, all_templates[Math.abs(random1.nextInt()) % templates.size()]);
                } else {
                    emailIntent.putExtra(EditDataActivity.EXTRA_DATA_MAIL_BODY, "You have write nothing");
                }

                PendingIntent emailPendingIntent = PendingIntent.getBroadcast(
                        context,
                        current.getEmailId(),
                        emailIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

                AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                am.setExact(AlarmManager.RTC_WAKEUP, current.getClock(), emailPendingIntent);

            }
        });


    }

    public void AlarmServie_off(Email current){
        MainActivity main = (MainActivity)context;
        Intent emailIntent = new Intent( main , SendMailAlarmReceiver.class);
        emailIntent.putExtra(main.EXTRA_MAIL_DATA, 0);
        PendingIntent emailPendingIntent = PendingIntent.getBroadcast(
                context,
                current.getEmailId(),
                emailIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(emailPendingIntent);
        Log.d("switch", "onCheckedChanged: alarm cancel");
    }
    @Override
    public void onBindViewHolder(EmailViewHolder holder, int position) {
        if (this.emails != null) {
            final Email current = this.emails.get(position);
            Log.d("Time", String.valueOf(current.getClock()));
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(current.getClock());
            holder.clockYearTextView.setText(DateTimeFormat.getYearString(cal));
            holder.clockMonthTextView.setText(DateTimeFormat.getMonthString(cal));
            holder.clockDayTextView.setText(DateTimeFormat.getDayString(cal));
            holder.clockHourTextView.setText(DateTimeFormat.getHourString(cal));
            holder.clockMinuteTextView.setText(DateTimeFormat.getMinuteString(cal));
            holder.clockIsOnSwitch.setChecked(current.getClockIsOn());

            holder.clockIsOnSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        AlarmService_on(current);
                    }
                    else {
                        AlarmServie_off(current);
                    }
                }
            });

            holder.SubjectTextView.setText(current.getSubject());
            holder.toTextView.setText(current.getTo());
        } else {
            holder.toTextView.setText(R.string.no_to);
        }
    }

    public void setEmails(List<Email> emails) {
        this.emails = emails;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (this.emails != null)
            return this.emails.size();
        else return 0;
    }

    public Email getEmailAtPosition(int position) {
        return this.emails.get(position);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        EmailListAdapter.clickListener = clickListener;
    }

    public class EmailViewHolder extends RecyclerView.ViewHolder {

        private final TextView clockYearTextView;
        private final TextView clockMonthTextView;
        private final TextView clockDayTextView;
        private final TextView clockHourTextView;
        private final TextView clockMinuteTextView;

        private final Switch clockIsOnSwitch;

        private final TextView SubjectTextView;
        private final TextView toTextView;

        private EmailViewHolder(View itemView) {
            super(itemView);
            this.clockYearTextView = itemView.findViewById(R.id.clock_year);
            this.clockMonthTextView = itemView.findViewById(R.id.clock_month);
            this.clockDayTextView = itemView.findViewById(R.id.clock_day);
            this.clockHourTextView = itemView.findViewById(R.id.clock_hour);
            this.clockMinuteTextView = itemView.findViewById(R.id.clock_minute);

            this.clockIsOnSwitch = itemView.findViewById(R.id.clock_is_on);

            this.SubjectTextView = itemView.findViewById(R.id.subject);
            this.toTextView = itemView.findViewById(R.id.to);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    EmailListAdapter.clickListener.onItemClick(view, EmailViewHolder.this.getAdapterPosition());
                }
            });
        }
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }
}

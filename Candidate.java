package com.example.vms;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class Candidate {
    private String Id, Image, FirstName, LastName, Phone, Location, Date, Time;

    public Candidate() { }

    public Candidate(String Id, String Image, String FirstName, String LastName, String Phone, String Location, String Date, String Time) {
        this.Id = Id;
        this.Image = Image;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Phone = Phone;
        this.Location = Location;
        this.Date = Date;
        this.Time = Time;
    }

    public String getId() {
        return Id;
    }

    public String getImage() {
        return Image;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getPhone() {
        return Phone;
    }

    public String getLocation() {
        return Location;
    }

    public String getDate() {
        return Date;
    }

    public String getTime() {
        return Time;
    }
}

class CandidateAdapter extends RecyclerView.Adapter<CandidateAdapter.CandidateViewHolder> implements Filterable {

    private Context context;
    private List<Candidate> candidateList;
    private List<Candidate> filterList;

    public CandidateAdapter(Context context, List<Candidate> candidateList) {
        this.context = context;
        this.candidateList = candidateList;
        this.filterList = candidateList;
    }

    @NonNull
    @Override
    public CandidateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_item, parent, false);
        return new CandidateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CandidateViewHolder holder, final int position) {
        holder.tvId.setText(candidateList.get(position).getId());
        Glide.with(context).load(candidateList.get(position).getImage()).into(holder.imageView);
        holder.tvFirstName.setText(candidateList.get(position).getFirstName());
        holder.tvLastName.setText(candidateList.get(position).getLastName());
        holder.tvDate.setText(candidateList.get(position).getDate());
        holder.tvTime.setText(candidateList.get(position).getTime());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InformationActivity.class);
                intent.putExtra("firstName", candidateList.get(position).getFirstName());
                intent.putExtra("lastName", candidateList.get(position).getLastName());
                intent.putExtra("phone", candidateList.get(position).getPhone());
                intent.putExtra("location", candidateList.get(position).getLocation());
                intent.putExtra("date", candidateList.get(position).getDate());
                intent.putExtra("time", candidateList.get(position).getTime());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return candidateList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results=new FilterResults();
                if(constraint != null && constraint.length() > 0)
                {
                    constraint = constraint.toString().toUpperCase();
                    ArrayList<Candidate> filtered = new ArrayList<>();
                    for (int i=0;i<filterList.size();i++)
                    {
                        if(filterList.get(i).getFirstName().toUpperCase().contains(constraint))
                        {
                            filtered.add(filterList.get(i));
                        }
                    }
                    results.count=filtered.size();
                    results.values=filtered;
                }
                else {
                    results.count=filterList.size();
                    results.values=filterList;
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                candidateList = (ArrayList<Candidate>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class CandidateViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvId, tvFirstName, tvLastName, tvDate, tvTime;

        public CandidateViewHolder(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.textViewId);
            imageView = itemView.findViewById(R.id.imageView);
            tvFirstName = itemView.findViewById(R.id.textViewFirstName);
            tvLastName = itemView.findViewById(R.id.textViewLastName);
            tvDate = itemView.findViewById(R.id.textViewDate);
            tvTime = itemView.findViewById(R.id.textViewTime);
        }
    }
}
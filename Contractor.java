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

public class Contractor {
    private String Id, Image, FirstName, LastName, Phone, Location, Date, Time, CompanyName, Service;

    public Contractor() { }

    public Contractor(String Id, String Image, String FirstName, String LastName, String Phone, String Location, String Date, String Time, String CompanyName, String Service) {
        this.Id = Id;
        this.Image = Image;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Phone = Phone;
        this.Location = Location;
        this.Date = Date;
        this.Time = Time;
        this.CompanyName = CompanyName;
        this.Service = Service;
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

    public String getCompanyName() {
        return CompanyName;
    }

    public String getService() {
        return Service;
    }
}

class ContractorAdapter extends RecyclerView.Adapter<ContractorAdapter.ContractorViewHolder> implements Filterable {

    private Context context;
    private List<Contractor> contractorList;
    private List<Contractor> filterList;

    public ContractorAdapter(Context context, List<Contractor> contractorList) {
        this.context = context;
        this.contractorList = contractorList;
        this.filterList = contractorList;
    }

    @NonNull
    @Override
    public ContractorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_item, parent, false);
        return new ContractorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContractorViewHolder holder, final int position) {
        holder.tvId.setText(contractorList.get(position).getId());
        Glide.with(context).load(contractorList.get(position).getImage()).into(holder.imageView);
        holder.tvFirstName.setText(contractorList.get(position).getFirstName());
        holder.tvLastName.setText(contractorList.get(position).getLastName());
        holder.tvDate.setText(contractorList.get(position).getDate());
        holder.tvTime.setText(contractorList.get(position).getTime());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InformationActivity.class);
                intent.putExtra("firstName", contractorList.get(position).getFirstName());
                intent.putExtra("lastName", contractorList.get(position).getLastName());
                intent.putExtra("phone", contractorList.get(position).getPhone());
                intent.putExtra("location", contractorList.get(position).getLocation());
                intent.putExtra("date", contractorList.get(position).getDate());
                intent.putExtra("time", contractorList.get(position).getTime());
                intent.putExtra("companyName", contractorList.get(position).getCompanyName());
                intent.putExtra("service", contractorList.get(position).getService());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contractorList.size();
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
                    ArrayList<Contractor> filtered = new ArrayList<>();
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
                contractorList = (ArrayList<Contractor>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ContractorViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvId, tvFirstName, tvLastName, tvDate, tvTime;

        public ContractorViewHolder(@NonNull View itemView) {
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
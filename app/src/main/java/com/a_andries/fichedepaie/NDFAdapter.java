package com.a_andries.fichedepaie;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anupcowkur.reservoir.Reservoir;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NDFAdapter extends RecyclerView.Adapter<NDFAdapter.NDFViewHolder> {

    private List<NDF> items = new ArrayList<>();
    private Context context;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    NDFAdapter(Context context) {
        this.context = context;
        loadData();
    }

    public void loadData() {
        try {
            if (Reservoir.contains("itemsList")) {
                Type resultType = new TypeToken<List<NDF>>() {
                }.getType();
                items = Reservoir.get("itemsList", resultType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public NDFViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.ndf_row, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        return new NDFViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull NDFViewHolder holder, int position) {
        final NDF current = items.get(position);

        holder.name.setText(current.getPracticianName());
        holder.dateVisit.setText(dateFormat.format(current.getDateOfVisit().getTime()));
        holder.total.setText(String.format(Locale.getDefault(), "%.2f", current.getTotalAmount()));
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NDFView.class);

                intent.putExtra(NDFView.NDF_EXTRA_KEY, current);
                context.startActivity(intent);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    List<NDF> list;

                    if (Reservoir.contains("itemsList")) {
                        Type resultType = new TypeToken<List<NDF>>() {
                        }.getType();
                        list = Reservoir.get("itemsList", resultType);
                        Reservoir.delete("itemsList");
                    } else
                        list = new ArrayList<>();
                    items.remove(current);
                    for (int i = 0; i < list.size(); i++) {
                        NDF c = list.get(i);

                        Log.e("e", "c.getKey: " + c.getKey() + " current.getKey: " + current.getKey());
                        if (c.getKey().equals(current.getKey())) {
                            Log.e("e", "$$$$$$");
                            list.remove(i);
                            break;
                        }
                    }
                    notifyDataSetChanged();
                    Reservoir.put("itemsList", list);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class NDFViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name_of_practician)
        TextView name;
        @BindView(R.id.date_visit)
        TextView dateVisit;
        @BindView(R.id.total)
        TextView total;
        @BindView(R.id.delete)
        TextView delete;
        @BindView(R.id.root)
        View root;

        public NDFViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

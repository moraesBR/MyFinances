package senac.myfinances.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import senac.myfinances.R;
import senac.myfinances.models.Finance;

public class FinanceAdapter extends RecyclerView.Adapter {

    private List<Finance> finances;
    private Context context;

    public FinanceAdapter(List<Finance> finances, Context context) {
        this.finances = finances;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(context).inflate(R.layout.item_finance, parent, false);
        return new FinanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FinanceViewHolder financeViewHolder = (FinanceViewHolder) holder;

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class FinanceViewHolder extends RecyclerView.ViewHolder{

        AppCompatTextView tvDate;
        AppCompatTextView tvValue;
        AppCompatImageView imgSpentType;

        public FinanceViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

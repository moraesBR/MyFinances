package senac.myfinances.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import senac.myfinances.R;
import senac.myfinances.models.Finance;
import senac.myfinances.models.SpendType;

public class FinanceAdapter extends RecyclerView.Adapter {

    private List<Finance> finances;
    private Context context;
    private View.OnClickListener mOnItemClickListener;

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }

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

        Finance finance = finances.get(position);


        financeViewHolder.tvDate.setText(finance.getData().format(DateTimeFormatter.ofPattern("YYYY/MM/dd")));
        financeViewHolder.tvValue.setText(String.valueOf(finance.getIncoming()));
        if(finance.getType() == SpendType.IN)
            financeViewHolder.ivSpentType.setImageResource(R.drawable.ic_gain);
        else
            financeViewHolder.ivSpentType.setImageResource(R.drawable.ic_spent);
    }

    @Override
    public int getItemCount() {
        return finances.size();
    }

    class FinanceViewHolder extends RecyclerView.ViewHolder{

        AppCompatTextView tvDate;
        AppCompatTextView tvValue;
        AppCompatImageView ivSpentType;
        AppCompatImageView ivRemove;

        public FinanceViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvValue = itemView.findViewById(R.id.tvIncoming);
            ivSpentType = itemView.findViewById(R.id.ivTypeIncoming);
            ivRemove = itemView.findViewById(R.id.ivRemove);
            ivRemove.setClickable(true);
        }
    }
}

package senac.myfinances.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import senac.myfinances.MainActivity;
import senac.myfinances.R;
import senac.myfinances.models.Finance;
import senac.myfinances.models.SpendType;

import static senac.myfinances.MainActivity.financeDB;

public class FinanceAdapter extends RecyclerView.Adapter {

    private List<Finance> finances;
    private Context context;

    private View.OnClickListener mOnItemClickListener;

    public void setOnItemClickListener() {
        mOnItemClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Step 4 of 4: Finally call getTag() on the view.
                // This viewHolder will have all required values.
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();

                int position = viewHolder.getAdapterPosition();

                Finance finance = finances.get(position);

                Toast.makeText(context,"Position" + finance.getId(),Toast.LENGTH_SHORT).show();
            }
        };
    }

    public FinanceAdapter(List<Finance> finances, Context context) {
        this.finances = finances;
        this.context = context;
        this.setOnItemClickListener();
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

        financeViewHolder.ivRemove.setOnClickListener(view -> removerItem(position));
    }

    @Override
    public int getItemCount() {
        return finances.size();
    }

    /*private void removerItem(int position) {
        financeDB.delete(finances.get(position).getId());
        finances.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, finances.size());
    }*/

    private void removerItem(int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder
                .setMessage("Tem certeza?")
                .setPositiveButton("Sim", (dialog, id) -> {
                    financeDB.delete(finances.get(position).getId());
                    finances.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, finances.size());
                })
                .setNegativeButton("Não", (DialogInterface dialog, int id) -> dialog.cancel()
                )
        .show();

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

            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }
    }
}

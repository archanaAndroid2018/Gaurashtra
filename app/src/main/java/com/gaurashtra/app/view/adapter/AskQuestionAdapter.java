package com.gaurashtra.app.view.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jsoup.Jsoup;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gaurashtra.app.R;
import com.gaurashtra.app.model.bean.ProductDetails.AskQuestion;
import com.gaurashtra.app.model.bean.ProductRemainingResult;
import com.gaurashtra.app.view.activity.ProductDetailActivity;

public class AskQuestionAdapter extends RecyclerView.Adapter<AskQuestionAdapter.AskQuestionViewHolder> {
    Context context;
    ArrayList<ProductRemainingResult.Result.Product.AskQuestion> askQuestionList;


    public AskQuestionAdapter(ProductDetailActivity productDetailActivity, List<ProductRemainingResult.Result.Product.AskQuestion> askQuestionList) {
        this.context= productDetailActivity;
        this.askQuestionList= (ArrayList<ProductRemainingResult.Result.Product.AskQuestion>) askQuestionList;
    }

    @NonNull
    @Override
    public AskQuestionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ask_question_item_layout,viewGroup,false);
        AskQuestionViewHolder vh= new AskQuestionViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AskQuestionViewHolder holder, int i) {
        String questString= Jsoup.parse("<html>"+askQuestionList.get(i).getQuestionerContent()+"</html>").text();
        String ansString= Jsoup.parse("<html>"+askQuestionList.get(i).getReplayerContent()+"</html>").text();
        holder.tvUserName.setText(askQuestionList.get(i).getQuestionerName());
        holder.tvQuestion.setText(Html.fromHtml(questString));
        holder.tvAnswer.setText(Html.fromHtml(ansString));

        String orignalAskDate= askQuestionList.get(i).getReplayerDate();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String originalReplyDate= askQuestionList.get(i).getQuestionerDate();
        DateFormat outputformat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date1 = null, date2= null;
        String output1 = null, output2= null;
        try{
            date1= df.parse(orignalAskDate);
            output1 = outputformat.format(date1);
            System.out.println(output1);

            date2= df.parse(originalReplyDate);
            output2 = outputformat.format(date2);
            System.out.println(output2);
        }catch(ParseException pe){
            pe.printStackTrace();
        }
        String[] askDateTime= output1.split("\\s+");
        String[] replyDateTime= output2.split("\\s+");

        String strAskDate= askDateTime[0];
        String strReplyDate= replyDateTime[0];

        holder.tvAskedDate.setText(strAskDate);
        holder.tvReplier.setText("("+askQuestionList.get(i).getReplayerName()+", "+strReplyDate+")");
    }

    @Override
    public int getItemCount() {
        return askQuestionList.size();
    }

    public class AskQuestionViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserName, tvAskedDate, tvQuestion, tvAnswer, tvReplier;
        public AskQuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName= itemView.findViewById(R.id.tv_askQues_user);
            tvQuestion= itemView.findViewById(R.id.tv_askQues_ques);
            tvAnswer= itemView.findViewById(R.id.tv_askQues_ans);
            tvAskedDate= itemView.findViewById(R.id.tv_askQues_date);
            tvReplier= itemView.findViewById(R.id.tv_askQues_replier);
        }
    }
}

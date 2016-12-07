package com.co.nightowl;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import com.androidnetworking.error.ANError;
import com.braintreepayments.cardform.OnCardFormSubmitListener;
import com.braintreepayments.cardform.view.CardForm;
import com.co.nightowl.utils.AppToast;
import com.co.nightowl.utils.NetworkThread;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rizvan Hawaldar on 06/12/16.
 */
public class CardInfoActivity extends AppCompatActivity{
    private Activity activity;
    @Bind(R.id.input_card_name) EditText cardInputTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_info_layout);
        activity = CardInfoActivity.this;
        ButterKnife.bind(activity);
        showCardForm();
    }

    private void showCardForm(){
        final CardForm cardForm = (CardForm) findViewById(R.id.card_form);
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(false)
                .mobileNumberRequired(true)
                .mobileNumberExplanation("Acknowledgment will be sent on this number.")
                .actionLabel("Purchase")
                .setup(activity);
        cardForm.setOnCardFormSubmitListener(new OnCardFormSubmitListener() {
            @Override
            public void onCardFormSubmit() {
                /**
                 * Is Card Valid
                 */
                if (cardInputTextView.getText().toString()!=null && cardInputTextView.getText().toString().trim().length()> 0 &&
                        cardForm.isValid()) {
                     String exp = cardForm.getExpirationMonth() +"/"+cardForm.getExpirationYear();
                        goForRent(cardInputTextView.getText().toString(),cardForm.getCardNumber(),exp,cardForm.getCvv());
                }
                else{
                    AppToast.show(activity,"Please update valid card details.");
                }
            }
        });
    }

    private void goForRent(String name,String number , String experiation , String code){
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("number",number);
            jsonObject.put("name",name);
            jsonObject.put("expiration",experiation);
            jsonObject.put("code",code);
            final NetworkThread networkThread = new NetworkThread();
            networkThread.postRentals(jsonObject,listener);

        }
        catch (Exception e){
            e.getLocalizedMessage();
        }
    }

    NetworkThread.DataResponseListener listener = new NetworkThread.DataResponseListener() {
        @Override
        public void onResponse(JSONObject response) {
            Log.d("ONRESPONSE ",response.toString());
            AppToast.show(activity,"Rent operation has been completed successfully");
            finish();
        }

        @Override
        public void onError(ANError anError) {
            AppToast.show(activity,"An Error Occured");
        }
    };
}

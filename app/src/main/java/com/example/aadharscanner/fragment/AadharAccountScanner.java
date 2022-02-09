package com.example.aadharscanner.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.aadharscanner.R;
import com.example.aadharscanner.utils.AppUtils;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

import butterknife.BindView;
import butterknife.OnClick;

public class AadharAccountScanner extends BaseFragment {

    @Nullable
    @BindView(R.id.aadharScannerIV)
    ImageView aadharScannerIV;

    @Nullable
    @BindView(R.id.enterAadharEt)
    EditText enterAadharEt;


    XmlPullParser parser;

    String uid, name, gender, yob, co, house, loc, vtc, dist, state, pc;
    String aadharUid;

    @Nullable
    ProgressDialog progressDialog;


    @NonNull
    public static AadharAccountScanner newInstance() {
        AadharAccountScanner aadharAccountScanner = new AadharAccountScanner();
        return aadharAccountScanner;
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.aadhar_account_fragment;
    }

    @Override
    public void onFragmentReady() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getContext().getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @OnClick(R.id.aadharScannerIV)
    void imageView() {
        //Toast.makeText(getContext(),"hii done",Toast.LENGTH_SHORT).show();
        IntentIntegrator integrator = new IntentIntegrator(getActivity());
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setPrompt(getContext().getResources().getString(R.string.scan_brcd));
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
        integrator.forSupportFragment(this).initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.dualPane);
        // super.onActivityResult(requestCode, resultCode, data);
        //  super.startActivityForResult();requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(getContext(), getString(R.string.canceld), Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(getContext(), getString(R.string.done), Toast.LENGTH_LONG).show();
                XmlPullParserFactory pullParserFactory;
                try {
                    pullParserFactory = XmlPullParserFactory.newInstance();
                    parser = pullParserFactory.newPullParser();
                    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    parser.setInput(new StringReader(result.getContents()));
                    // processParsing(parser);
                } catch (Exception e) {
                    AppUtils.getInstance().showLog("EXCEPTION" + e, AadharAccountScanner.class);
                }
                AppUtils.getInstance().showLog("response", AadharAccountScanner.class);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    private void processParsing(@NonNull XmlPullParser parser) throws IOException, XmlPullParserException {
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_DOCUMENT) {
                AppUtils.getInstance().showLog("startDocuments", AadharAccountScanner.class);
            } else if (eventType == XmlPullParser.START_TAG) {
                AppUtils.getInstance().showLog("Start tag " + parser.getName(), AadharAccountScanner.class);
                if (parser.getName().equalsIgnoreCase("PrintLetterBarcodeData")) {
                    uid = parser.getAttributeValue(null, "uid");
                    aadharUid = enterAadharEt.getText().toString();
                    if (aadharUid == null || aadharUid.equalsIgnoreCase("")) {
                        enterAadharEt.setText(uid);
                    } else {
                        if (aadharUid.equalsIgnoreCase(uid)) {
                            progressDialog.show();
                            name = parser.getAttributeValue(null, "name");
                            gender = parser.getAttributeValue(null, "gender");
                            co = parser.getAttributeValue(null, "co");
                            yob = parser.getAttributeValue(null, "yob");
                            house = parser.getAttributeValue(null, "house");
                            loc = parser.getAttributeValue(null, "loc");
                            vtc = parser.getAttributeValue(null, "vtc");
                            dist = parser.getAttributeValue(null, "dist");
                            state = parser.getAttributeValue(null, "state");
                            pc = parser.getAttributeValue(null, "pc");
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    progressDialog.dismiss();
                                   /* detaillayout.setVisibility(View.VISIBLE);
                                    entredNameEt.setText(name);
                                    enterGenderEt.setText(gender);
                                    fatherNameEt.setText(co);
                                    yobEt.setText(yob);
                                    addressEt.setText(house+", "+loc+", "+vtc+", "+dist+", "+state+", "+pc);*/
                                }
                            }, 3000); // 3000

                        } else {
                            Toast.makeText(getContext(), getString(R.string.enter_aadhar_card_number_is_wrong), Toast.LENGTH_SHORT).show();
                        }
                    }
                    AppUtils.getInstance().showLog("Start tag UID " + uid, AadharAccountScanner.class);
                    //Toast.makeText(getContext(),"uid"+uid,Toast.LENGTH_SHORT).show();
                }

            } else if (eventType == XmlPullParser.END_TAG) {
                // System.out.println("End tag "+xpp.getName());
                AppUtils.getInstance().showLog("End tag " + parser.getName(), AadharAccountScanner.class);
            } else if (eventType == XmlPullParser.TEXT) {
                AppUtils.getInstance().showLog("Text" + parser.getText(), AadharAccountScanner.class);
            }
            eventType = parser.next();
        }
        AppUtils.getInstance().showLog("EndDocuments", AadharAccountScanner.class);
    }
}

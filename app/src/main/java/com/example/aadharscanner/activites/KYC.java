package com.example.aadharscanner.activites;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aadharscanner.R;
import com.example.aadharscanner.adapters.DocTypeAdapter;
import com.example.aadharscanner.database.GpsData;
import com.example.aadharscanner.database.GpsDataDao;
import com.example.aadharscanner.database.KycDocData;
import com.example.aadharscanner.database.KycDocSyncData;
import com.example.aadharscanner.database.KycDocSyncDataDao;
import com.example.aadharscanner.database.ShgMemberDetailsData;
import com.example.aadharscanner.database.ShgMemberDetailsDataDao;
import com.example.aadharscanner.database.VillageData;
import com.example.aadharscanner.database.VillageDataDao;
import com.example.aadharscanner.fragment.SelectLocationFragment;
import com.example.aadharscanner.utils.AppConstant;
import com.example.aadharscanner.utils.AppUtils;
import com.example.aadharscanner.utils.Cryptography;
import com.example.aadharscanner.utils.DialogFactory;
import com.example.aadharscanner.utils.FileManager;
import com.example.aadharscanner.utils.FileUtility;
import com.example.aadharscanner.utils.NetworkFactory;
import com.example.aadharscanner.utils.PreferenceFactory;
import com.example.aadharscanner.utils.PreferenceManager;
import com.example.aadharscanner.utils.SyncData;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.integration.android.IntentIntegrator;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.greenrobot.greendao.query.QueryBuilder;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.aadharscanner.utils.AppConstant.CAPTURE_BACK_IMAGE_REQUEST_CODE;
import static com.example.aadharscanner.utils.AppConstant.CAPTURE_FRONT_IMAGE_REQUEST_CODE;
import static com.example.aadharscanner.utils.AppConstant.IMAGE_COMPRESS_QLTY;

public class KYC extends AppCompatActivity {

    @Nullable
    @BindView(R.id.kyc_back_arrowIV)
    ImageView kyc_back_arrowIV;

    @Nullable
    @BindView(R.id.memberDetailsTV)
    TextView memberDetailsTV;

    @Nullable
    @BindView(R.id.kyc_doc_typeMBS)
    Spinner kycDockTypeMSB;

    @Nullable
    @BindView(R.id.document_idTIET)
    TextInputEditText document_idTIET;

    @Nullable
    @BindView(R.id.frontdocCV)
    CardView frontdocCV;

    @Nullable
    @BindView(R.id.front_image_titleTV)
    TextView front_image_titleTV;

    @Nullable
    @BindView(R.id.frontImageView)
    ImageView frontImageView;

    @Nullable
    @BindView(R.id.takefronimagetBTN)
    Button takefronimagetBTN;

    @Nullable
    @BindView(R.id.takeAgainfrontBTN)
    Button takeAgainfrontBTN;

    @Nullable
    @BindView(R.id.backdocCV)
    CardView backdocCV;

    @Nullable
    @BindView(R.id.back_image_titleTV)
    TextView back_image_titleTV;

    @Nullable
    @BindView(R.id.takebackImageBTN)
    Button takebackImageBTN;

    @Nullable
    @BindView(R.id.backImageView)
    ImageView backImageView;


    @Nullable
    @BindView(R.id.takeAgainbackImageBTN)
    Button takeAgainbackImageBTN;

    @Nullable
    @BindView(R.id.submitdetailsBTN)
    Button submitdetailsBTN;


    private Context context;
    private DocTypeAdapter kycDockTypeAdapter;
    private List<KycDocData> docTypeDataItems;

    @Nullable
    private String docId = "", docType = "", docName = "", docNoLength = "", enteredDocId = "",
            memberCode, villageCode, shgCode, shgMemberName;
    @Nullable
    private Bitmap bmp;
    @Nullable
    private byte[] frontImageByteArray = null;
    @Nullable
    private byte[] backImageByteArray = null;
    private boolean isValidDoc = false;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_k_y_c);
        ButterKnife.bind(this);
        context = this;
//        kycDockTypeMSB.setFocusableInTouchMode(false);

        villageCode = PreferenceFactory.getInstance().getSharedPrefrencesData("villageCode", context);
        shgCode = PreferenceFactory.getInstance().getSharedPrefrencesData("shgCode", context);
        memberCode = PreferenceFactory.getInstance().getSharedPrefrencesData("shgMemberCode", context);
        shgMemberName = PreferenceFactory.getInstance().getSharedPrefrencesData("shgMemberName", context);

        memberDetailsTV.setText(shgMemberName + " (" + memberCode + ")");

        initList();


        kycDockTypeAdapter = new DocTypeAdapter(context, docTypeDataItems);
        kycDockTypeMSB.setAdapter(kycDockTypeAdapter);
        kycDockTypeMSB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(@NonNull AdapterView<?> parent, View view, int position, long id) {
                KycDocData docTypeDataItem = (KycDocData) parent.getItemAtPosition(position);
                /*take document id here*/
                docName = docTypeDataItem.getDocName();
                docType = docTypeDataItem.getDocType();
                docId = docTypeDataItem.getDocId();
                docNoLength = docTypeDataItem.getDocNolength();
                AppUtils.getInstance().showLog("docName=" + docName + ", docType=" + docType + ", docId" + docId + "docNoLength=" + docNoLength, KYC.class);

                switch (docId) {
                    case "1":
                        document_idTIET.setInputType(InputType.TYPE_CLASS_NUMBER);
                        document_idTIET.setFilters(new InputFilter[]{AppUtils.getInstance().setInputFilter(AppConstant.AADHAR_DIGITS),
                                new LengthFilter(Integer.parseInt(docNoLength.trim()))});
                        break;
                    case "2":
                        document_idTIET.setInputType(InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS);
                        document_idTIET.setFilters(new InputFilter[]{AppUtils.getInstance().setInputFilter(AppConstant.PAN_or_DL_DIGITS),
                                new LengthFilter(Integer.parseInt(docNoLength.trim()))});
                        break;
                    case "3":

                    case "9":
                        document_idTIET.setInputType(InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS);
                        InputFilter inputFilter = new InputFilter() {
                            @Override
                            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                                for (int i = start; i < end; ++i) {
                                    if (!Pattern.compile("[ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789]*").matcher(String.valueOf(source.charAt(i))).matches()) {
                                        document_idTIET.setText("");
                                        return "";
                                    }
                                }
                                return null;
                            }
                        };
                        document_idTIET.setFilters(new InputFilter[]{inputFilter, new LengthFilter(Integer.parseInt(docNoLength.trim()))});

                        /*             document_idTIET.setFilters(new LengthFilter[]{new LengthFilter(Integer.parseInt(docNoLength.trim()))});*/
                        break;

                    case "4":
                    case "5":
                    case "6":
                    case "7":
                    case "8":
                    case "10":
                        document_idTIET.setInputType(InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS);
                        break;
                }


                //  Toast.makeText(context, docTypeDataItem.getDocType() + "  is selcted"+"docType="+docType, Toast.LENGTH_LONG).show();

                if (docType.equalsIgnoreCase(AppConstant.KYC_DOC_TYPE)) {
                    frontdocCV.setVisibility(View.VISIBLE);
                    backdocCV.setVisibility(View.GONE);
                    bmp = null;
                    backImageByteArray = null;
                    front_image_titleTV.setText(getResources().getString(R.string.doc_image_title));
                } else {
                    frontdocCV.setVisibility(View.VISIBLE);
                    front_image_titleTV.setText(getResources().getString(R.string.doc_image_front_title));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        document_idTIET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                switch (docId) {
                    case "1":
                        isValidDoc = AppUtils.isValidAadharNumber(String.valueOf(s));
                        if (!isValidDoc) {
                            document_idTIET.setError(getString(R.string.error_invalid_aadhar));
                        }
                        break;
                    case "2":
                        isValidDoc = AppUtils.isValidPanCardNo(String.valueOf(s));
                        if (!isValidDoc) {
                            document_idTIET.setError(getString(R.string.error_invalid_pan));
                        }
                        break;
                    case "3":
                        isValidDoc = AppUtils.isValidLicenseNo(String.valueOf(s));
                        if (!isValidDoc) {
                            document_idTIET.setError(getString(R.string.error_invalid_dl));
                        }
                        break;
                    case "4":
                    case "5":
                    case "6":
                    case "7":
                    case "8":
                    case "10":
                        if (document_idTIET.getText().toString().trim().length() < 3) {
                            document_idTIET.setError(getString(R.string.error_doc_id));
                            isValidDoc = false;
                        } else isValidDoc = true;

                        break;

                    case "9":
                        isValidDoc = AppUtils.isValidPassportNo(String.valueOf(s));
                        if (!isValidDoc) {
                            document_idTIET.setError(getString(R.string.error_invalid_passport));
                        }
                        break;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @OnClick(R.id.takefronimagetBTN)
    public void takeFrontImage() {
        if (isValidDoc) {
            enteredDocId = document_idTIET.getText().toString().trim();
            if (enteredDocId.isEmpty() || enteredDocId.equalsIgnoreCase("")) {
                document_idTIET.setError(getResources().getString(R.string.error_doc_id));
            } else {
              QueryBuilder<ShgMemberDetailsData> shgMemberDetailsDataQB =SplashScreen.getInstance().getDaoSession()
                        .getShgMemberDetailsDataDao().queryBuilder();
                int shgMemberDetailsDataListSize= shgMemberDetailsDataQB.where(ShgMemberDetailsDataDao.Properties.VillageCode.eq(villageCode)
                        , shgMemberDetailsDataQB.and(ShgMemberDetailsDataDao.Properties.ShgCode.eq(shgCode)
                                ,ShgMemberDetailsDataDao.Properties.KycDocId.eq(enteredDocId))).build().list().size();
                if (shgMemberDetailsDataListSize>0){
                    document_idTIET.setError(getResources().getString(R.string.toast_document_exists));
                }else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,
                            CAPTURE_FRONT_IMAGE_REQUEST_CODE);
                }
            }
        } else {
            Toast.makeText(context, getString(R.string.error_doc_id), Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.takeAgainfrontBTN)
    public void takeAgainFrontImage() {
        bmp = null;
        backImageByteArray = null;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,
                CAPTURE_FRONT_IMAGE_REQUEST_CODE);
    }


    @OnClick(R.id.takebackImageBTN)
    public void takeBackImage() {
        if (!(frontImageByteArray == null)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,
                    CAPTURE_BACK_IMAGE_REQUEST_CODE);
        }
    }

    @OnClick(R.id.takeAgainbackImageBTN)
    public void takeAgainBackImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,
                CAPTURE_BACK_IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAPTURE_FRONT_IMAGE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap bmp = (Bitmap) data.getExtras().get("data");
                frontImageView.setImageBitmap(bmp);
                takeAgainfrontBTN.setVisibility(View.VISIBLE);
                takefronimagetBTN.setVisibility(View.GONE);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, IMAGE_COMPRESS_QLTY, baos);
                frontImageByteArray = baos.toByteArray();
                if (docType.equalsIgnoreCase(AppConstant.KYC_DOC_TYPE)) {

                } else {
                    backdocCV.setVisibility(View.VISIBLE);
                }
                AppUtils.getInstance().showLog("byteArray" + frontImageByteArray, AadharAccountActivity.class);
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(context, getString(R.string.toast_error_front_photo), Toast.LENGTH_LONG).show();
        } else {
            if (resultCode == Activity.RESULT_OK) {
                takeAgainbackImageBTN.setVisibility(View.VISIBLE);
                takebackImageBTN.setVisibility(View.GONE);
                bmp = (Bitmap) data.getExtras().get("data");
                backImageView.setImageBitmap(bmp);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, IMAGE_COMPRESS_QLTY, baos);
                backImageByteArray = baos.toByteArray();
                AppUtils.getInstance().showLog("byteArray" + backImageByteArray, AadharAccountActivity.class);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(context, getString(R.string.toast_error_back_photo), Toast.LENGTH_LONG).show();
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.submitdetailsBTN)
    public void submitDetails() {
        View view = findViewById(R.id.submitdetailsBTN);

        /*docId,enteredDocId,frontImageByteArray,backImageByteArray*/

        QueryBuilder<KycDocSyncData> kycDocSyncDataQueryBuilder = SplashScreen.getInstance().getDaoSession().getKycDocSyncDataDao().queryBuilder();
        List<KycDocSyncData> kycDocSyncDataList = kycDocSyncDataQueryBuilder.where(KycDocSyncDataDao.Properties.Village_code.eq(villageCode),
                kycDocSyncDataQueryBuilder.or(KycDocSyncDataDao.Properties.MemberCode.eq(memberCode)
                        , KycDocSyncDataDao.Properties.DocNo.eq(enteredDocId.trim()))).build().list();
        AppUtils.getInstance().showLog("kycDocSyncDataList" + kycDocSyncDataList.size(), KYC.class);
        if (frontImageByteArray != null) {
            if (docType.equalsIgnoreCase(AppConstant.KYC_DOC_TYPE)) {
                if (kycDocSyncDataList.size() > 0) {
                    showSnackBar(view, getString(R.string.toast_document_exists));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            AppUtils.getInstance().makeIntent(context, KYC.class, true);
                        }
                    }, 2000);
                    //Toast.makeText(context,getString(R.string.toast_document_exists),Toast.LENGTH_LONG).show();
                } else {
                    saveKycDocdataInLocal();
                }
            } else {
                if (backImageByteArray != null) {
                    if (kycDocSyncDataList.size() > 0) {
                        // Toast.makeText(context,getString(R.string.toast_document_exists),Toast.LENGTH_LONG).show();
                        showSnackBar(view, getString(R.string.toast_document_exists));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                AppUtils.getInstance().makeIntent(context, KYC.class, true);
                            }
                        }, 2000);

                    } else {
                        saveKycDocdataInLocal();
                    }
                } else {
                    showSnackBar(view, getString(R.string.toast_take_back_image));
                    // Toast.makeText(context,getString(R.string.toast_take_back_image),Toast.LENGTH_LONG).show();
                }
            }
        } else {
            showSnackBar(view, getString(R.string.toast_take_front_image));
            // Toast.makeText(context,getString(R.string.toast_take_front_image),Toast.LENGTH_LONG).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void saveKycDocdataInLocal() {
        KycDocSyncData kycDocSyncData1 = new KycDocSyncData();
        submitdetailsBTN.setEnabled(false);

        kycDocSyncData1.setVillage_code(villageCode);
        kycDocSyncData1.setShgCode(shgCode);
        kycDocSyncData1.setId(null);
        kycDocSyncData1.setMemberCode(memberCode);
        kycDocSyncData1.setDocNo(enteredDocId);
        kycDocSyncData1.setDocId(docId);
        kycDocSyncData1.setDocFrontImage(frontImageByteArray);

        if (docType.equalsIgnoreCase(AppConstant.KYC_DOC_TYPE)) {
            kycDocSyncData1.setDocBackImage(null);
        } else {
            kycDocSyncData1.setDocBackImage(backImageByteArray);
        }
        kycDocSyncData1.setSyncStatus("0");

        SplashScreen.getInstance().getDaoSession().getKycDocSyncDataDao().insert(kycDocSyncData1);

        updateKycStatusOfMemDetailsData();

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();

        if (NetworkFactory.isInternetOn(KYC.this)) {
            syncKycDocSyncdata();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                    String kycDocVolleyError = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getKycDocApiVolleyError(), context);
                    if (kycDocVolleyError.equalsIgnoreCase(AppConstant.KYC_DOC_API_VOLLEY_ERROR)) {
                        PreferenceFactory.getInstance().removeSharedPrefrencesData(PreferenceManager.getKycDocApiVolleyError(), context);
                        // updateKycStatusOfMemDetailsData();
                        DialogFactory.getInstance().showAlertDialog(context, R.drawable.ic_launcher_background, getString(R.string.info)
                                , getString(R.string.KYC_DOC_API_VOLLEY_ERROR), getString(R.string.ok), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getPrefKeyShgMemberStatus(), "goToMemberList"
                                                , KYC.this);
                                        AppUtils.getInstance().makeIntent(KYC.this, HomeActivity.class, true);
                                    }
                                }, null, null, false);
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context, getString(R.string.data_sync_msg), Toast.LENGTH_LONG).show();
                        PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getPrefKeyShgMemberStatus(), "goToMemberList"
                                , KYC.this);
                        AppUtils.getInstance().makeIntent(KYC.this, HomeActivity.class, true);
                    }
                }
            }, 6000);
        } else {

            //update kyc doc file

            updateKycDocSyncBackUpFile(context);
            progressDialog.dismiss();
            Toast.makeText(context, getString(R.string.data_save_msg), Toast.LENGTH_LONG).show();
            PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getPrefKeyShgMemberStatus(), "goToMemberList"
                    , KYC.this);

            AppUtils.getInstance().makeIntent(KYC.this, HomeActivity.class, false);
        }

    }

    private void updateKycStatusOfMemDetailsData() {
        List<ShgMemberDetailsData> shgMemberDetailsDataList = SplashScreen.getInstance().getDaoSession().getShgMemberDetailsDataDao().queryBuilder()
                .where(ShgMemberDetailsDataDao.Properties.ShgMemberCode.eq(memberCode)).build().list();

        for (ShgMemberDetailsData shgMemberDetailsData : shgMemberDetailsDataList) {
            shgMemberDetailsData.setKycStatus("1");
            SplashScreen.getInstance().getDaoSession().getShgMemberDetailsDataDao().update(shgMemberDetailsData);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void syncKycDocSyncdata() {

        PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getCallingApi(), AppConstant.CALLING_API_KYC, KYC.this);
        SyncData.getInstance().syncData(KYC.this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initList() {
        List<KycDocData> sortedList = SplashScreen.getInstance().getDaoSession().getKycDocDataDao().queryBuilder().build().list();
        docTypeDataItems = sortedList.stream()
                .sorted(Comparator.comparing(KycDocData::getDocName))
                .collect(Collectors.toList());

        KycDocData kycDocData=new KycDocData();
        kycDocData.setDocId("0");
        kycDocData.setDocInputType("0");
        kycDocData.setDocName("--Select--");
        kycDocData.setDocNolength("0");
        kycDocData.setDocType("0");
        docTypeDataItems.add(0,kycDocData);
    }


    @OnClick(R.id.kyc_back_arrowIV)
    public void backArrow() {
        PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getPrefKeyShgMemberStatus(), "goToMemberList"
                , KYC.this);
        AppUtils.getInstance().makeIntent(context, HomeActivity.class, true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getPrefKeyShgMemberStatus(), "goToMemberList"
                , KYC.this);
        AppUtils.getInstance().makeIntent(context, HomeActivity.class, true);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void updateKycDocSyncBackUpFile(@NonNull Context context) {
        try {
            SyncData syncData = SyncData.getInstance();
            syncData.getLogInfo(context);
            FileUtility.getInstance().createFileInMemory(FileManager.getInstance()
                            .getPathDetails(context)
                    , AppConstant.KYC_DOC_FILE_NAME, new Cryptography().encrypt(syncData.getKycSyncdata(context).toString()));
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    public void showSnackBar(View v, String msg) {
        //  View snackBarContView=LayoutInflater.from(context).inflate(R.layout.spinner_textview,null,false);

        Snackbar snackBar = Snackbar.make(v, msg, Snackbar.LENGTH_LONG).setAction("",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
        snackBar.setBackgroundTint(context.getResources().getColor(R.color.colorWhite));
        snackBar.setTextColor(context.getResources().getColor(R.color.colorRed));

        snackBar.show();
    }

}
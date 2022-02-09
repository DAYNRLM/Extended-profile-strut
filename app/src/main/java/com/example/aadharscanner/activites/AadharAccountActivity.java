package com.example.aadharscanner.activites;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.loader.content.Loader;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.example.aadharscanner.R;
import com.example.aadharscanner.database.AadharDetailSyncData;
import com.example.aadharscanner.database.AadharDetailSyncDataDao;
import com.example.aadharscanner.database.AccountDetailSyncData;
import com.example.aadharscanner.database.AccountDetailSyncDataDao;
import com.example.aadharscanner.database.AddMemberAadharDetail;
import com.example.aadharscanner.database.BankBranchDetailData;
import com.example.aadharscanner.database.BankBranchDetailDataDao;
import com.example.aadharscanner.database.BankDetailData;
import com.example.aadharscanner.database.BankDetailDataDao;
import com.example.aadharscanner.database.BankType;
import com.example.aadharscanner.database.BlockDaoDataDao;
import com.example.aadharscanner.database.GpsDataDao;
import com.example.aadharscanner.database.LoginInfoData;
import com.example.aadharscanner.database.ShgDetailDataDao;
import com.example.aadharscanner.database.ShgMemberDetailsData;
import com.example.aadharscanner.database.ShgMemberDetailsDataDao;
import com.example.aadharscanner.database.VillageDataDao;
import com.example.aadharscanner.utils.AppConstant;
import com.example.aadharscanner.utils.AppUtils;
import com.example.aadharscanner.utils.Cryptography;
import com.example.aadharscanner.utils.DialogFactory;
import com.example.aadharscanner.utils.FileManager;
import com.example.aadharscanner.utils.FileUtility;
import com.example.aadharscanner.utils.GpsTracker;
import com.example.aadharscanner.utils.PreferenceFactory;
import com.example.aadharscanner.utils.PreferenceManager;
import com.example.aadharscanner.utils.Recognizer;
import com.example.aadharscanner.utils.SingletonVolley;
import com.example.aadharscanner.utils.SyncData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.aadharscanner.utils.AppConstant.IMAGE_COMPRESS_QLTY;
import static com.example.aadharscanner.utils.AppConstant.textualParameters;

public class AadharAccountActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, Loader.OnLoadCompleteListener {

    @Nullable
    @BindView(R.id.aadharScannerIV1)
    ImageView aadharScannerIV;

    @Nullable
    @BindView(R.id.passbookImageView)
    ImageView passbookImageView;

    @Nullable
    @BindView(R.id.aadharImageView)
    ImageView aadharImageView;

    @Nullable
    @BindView(R.id.enterAadharEt)
    EditText enterAadharEt;

    @Nullable
    @BindView(R.id.enterAadhaeNameET)
    EditText enterAadhaeNameET;

    @Nullable
    @BindView(R.id.aadharGenderEt)
    EditText aadharGenderEt;

    @Nullable
    @BindView(R.id.aadharFatherNameEt)
    EditText aadharFatherNameEt;

    @Nullable
    @BindView(R.id.aadharYobEt)
    EditText aadharYobEt;

    @Nullable
    @BindView(R.id.aadharAddressEt)
    EditText aadharAddressEt;

    @Nullable
    @BindView(R.id.aadharMobileNumberEt)
    EditText aadharMobileNumberEt;

    @Nullable
    @BindView(R.id.aadharShgNameTv)
    TextView aadharShgNameTv;

    @Nullable
    @BindView(R.id.aadharShgMemberNameTv)
    TextView aadharShgMemberNameTv;

    @Nullable
    @BindView(R.id.locationTv)
    TextView locationTv;

    @Nullable
    @BindView(R.id.aadhar_link_status)
    TextView aadhar_link_status;

    @Nullable
    @BindView(R.id.bank_account_link_status)
    TextView bank_account_link_status;


    @Nullable
    @BindView(R.id.detailLinearLayout)
    LinearLayout aadharDetailLinearLayout;

    @Nullable
    @BindView(R.id.accDetailLinearLayout)
    LinearLayout accountDetailLinearLayout;

    @Nullable
    @BindView(R.id.photoLinearLayout)
    LinearLayout photoLinearLayout;

    @Nullable
    @BindView(R.id.aadharPhotoLinearLayout)
    LinearLayout aadharPhotoLinearLayout;

    @Nullable
    @BindView(R.id.accDetailCardView)
    CardView accDetailCardView;

    @Nullable
    @BindView(R.id.aadharDetailCardView)
    CardView aadharDetailCardView;

    @Nullable
    @BindView(R.id.aadharScanHeaderCV)
    CardView aadharScanHeaderCV;

    @Nullable
    @BindView(R.id.bankDetailsHeaderCV)
    CardView bankDetailsHeaderCV;

    @Nullable
    @BindView(R.id.spinner_BankTypeSpin)
    MaterialBetterSpinner spinner_BankTypeSpin;

    @Nullable
    @BindView(R.id.spinner_BankNameSpin)
    MaterialBetterSpinner spinner_BankNameSpin;

    @Nullable
    @BindView(R.id.spinner_BankBranchSpin)
    MaterialBetterSpinner spinner_BankBranchSpin;

    @Nullable
    @BindView(R.id.ifcsTv)
    EditText ifscTextView;

    @Nullable
    @BindView(R.id.accountNumberEt)
    TextInputEditText accountNumberEt;

    @Nullable
    @BindView(R.id.accNumberTIL)
    TextInputLayout accNumberTIL;

    @Nullable
    @BindView(R.id.confirmAccountNumberEt)
    TextInputEditText confirmAccountNumberEt;

    @Nullable
    @BindView(R.id.submitAccountBtn)
    Button submitAccountBtn;

    @Nullable
    @BindView(R.id.submitAadharBtn)
    Button submitAadharBtn;

    @Nullable
    @BindView(R.id.takePhotoBtn)
    Button takePhotoBtn;

    @Nullable
    @BindView(R.id.takeAgainBtn)
    Button takeAgainBtn;

    @Nullable
    @BindView(R.id.takAadhaarSelfiBtn)
    Button takAadhaarSelfiBtn;

    @Nullable
    @BindView(R.id.takeAgainSelfiBtn)
    Button takeAgainSelfiBtn;

    @Nullable
    @BindView(R.id.upload_concentLL)
    LinearLayout upload_concentLL;

    @Nullable
    @BindView(R.id.fileNameTV)
    ImageView fileNameTV;


    @Nullable
    @BindView(R.id.concent_textTV)
    TextView concent_textTV;


    XmlPullParser parser;

    @NonNull
    String uid, name, gender, yob, co, house, loc, vtc, dist, state, pc, cardScanStatus = "0", aadharAddress, scanStatus;
    String aadharUid = "";
    @NonNull
    String aadharMobileNumber = "";

    public final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
    private byte[] imageByteArray;

    public final int CAPTURE_IMAGE_ACTIVITY_SELFI_REQUEST_CODE = 1889;
    private byte[] imageSelfiByteArray;

    ProgressDialog progressDialog;

    //array adapter set on spinner for gp, block and village
    ArrayAdapter<String> bankNameArrayAdapter;
    ArrayAdapter<String> bankBranchNameArrayAdapter;

    //list for bank branch and bank type
    private List<BankType> bankTypeDataListItems;
    private List<BankDetailData> bankNameDataListItems;
    private List<BankBranchDetailData> bankBranchesDataListItems;

    //string array for set on adapter bank,branch and type
    private String[] bankNameArray;
    private String[] bankBranchArray;

    //get bank,type and name code click on item on spinner
    @NonNull
    private String bankTypeCode = "";
    private String bankNameCode = "";
    private String bankBranchCode = "";

    @NonNull
    String accNumber = "";
    @NonNull
    String confirmAccNumber = "";
    @NonNull
    String bankTypeNam = "";
    @NonNull
    String bankName = "";
    @NonNull
    String bankBranchName = "";
    String ifscCode = "";
    String bankAccLength = "";
    @Nullable
    String aadharLinkStatus, bankAccountLinkStatus;
    /*
        String aadharLinkCurrentStatus, bankAccountLinkCurrentStatus;
    */
    @Nullable
    String shgCode;
    @Nullable
    String shgMemberCode;
    @Nullable
    String blockCode, gpCode, villageCode;
    @NonNull
    String longitude = "";
    @NonNull
    String latitude = "";
    int accountLength;
    private Context context;

    /*for file uploading*/

    private String pdfFileName;
    public static final int FILE_PICKER_REQUEST_CODE = 1;
    private byte[] pdfByteArray;
    private TextRecognizer textRecognizer;
    private boolean isSyncable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aadhar_account);
        ButterKnife.bind(this);
        context = this;
        textRecognizer = Recognizer.getTextRecognizer();
        progressDialog = new ProgressDialog(AadharAccountActivity.this);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);
        AppUtils.getInstance().showLog("onCreate", AadharAccountActivity.class);

        AppUtils.getInstance().showLog("---", AadharAccountActivity.class);
        aadharLinkStatus = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getPrefKeyAadharLinkStatus()
                , AadharAccountActivity.this);
        bankAccountLinkStatus = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getPrefKeyBankAccountLinkStatus()
                , AadharAccountActivity.this);
     /*   aadharLinkCurrentStatus = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getAadharCurrentStatus()
                , AadharAccountActivity.this);
        bankAccountLinkCurrentStatus = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getBankCurrentStatus()
                , AadharAccountActivity.this);*/
        shgCode = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getPrefKeyShgCode()
                , AadharAccountActivity.this);
        shgMemberCode = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getPrefKeyShgMemberCode()
                , AadharAccountActivity.this);
        blockCode = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getPrefKeyLoginBlockCode()
                , AadharAccountActivity.this);
        gpCode = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getPrefKeyGpCode()
                , AadharAccountActivity.this);
        villageCode = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getPrefKeyVillageCode()
                , AadharAccountActivity.this);
        aadharShgNameTv.setText(getShgName());
        aadharShgMemberNameTv.setText(getShgMemberName());
        submitAccountBtn.setEnabled(false);
        submitAccountBtn.setBackgroundColor(getResources().getColor(R.color.colorButtonLightBlue));
        locationTv.setText(getLocation());

        AppUtils.getInstance().showLog("aadharLinkStatus=" + aadharLinkStatus + "bankAccountLinkStatus=" + bankAccountLinkStatus, AadharAccountActivity.class);
        if (aadharLinkStatus.trim().equalsIgnoreCase("1")
                && bankAccountLinkStatus.trim().equalsIgnoreCase("0")) {
            /*show view for bank */
            accDetailCardView.setVisibility(View.VISIBLE);
            bankDetailsHeaderCV.setVisibility(View.VISIBLE);
            /*show text for aadhar is linked*/
            aadhar_link_status.setVisibility(View.VISIBLE);
            aadharDetailCardView.setVisibility(View.GONE);
            initilaizAllList();
            spinner_BankBranchSpin.setFocusableInTouchMode(false);
            bindBankTypeData();
            SetListner();
        } else if (aadharLinkStatus.equalsIgnoreCase("0")
                && bankAccountLinkStatus.equalsIgnoreCase("1")) {
            aadharDetailCardView.setVisibility(View.VISIBLE);
            aadharScanHeaderCV.setVisibility(View.VISIBLE);
            upload_concentLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //launchPicker();
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,
                            FILE_PICKER_REQUEST_CODE);
                }
            });
            /*show bank account data is linked */
            bank_account_link_status.setVisibility(View.VISIBLE);
            accDetailCardView.setVisibility(View.GONE);
            bankDetailsHeaderCV.setVisibility(View.GONE);
        } else if (aadharLinkStatus.equalsIgnoreCase("0")
                && bankAccountLinkStatus.equalsIgnoreCase("0")) {
            upload_concentLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // launchPicker();
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,
                            FILE_PICKER_REQUEST_CODE);
                }
            });

            aadharDetailCardView.setVisibility(View.VISIBLE);
            aadharScanHeaderCV.setVisibility(View.VISIBLE);
        }


        enterAadharEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(@NonNull Editable s) {

          /*      if (!(fileNameTV.getText().toString().contains(".pdf"))) {
                    Toast.makeText(AadharAccountActivity.this, context.getString(R.string.toast_uploade_file), Toast.LENGTH_SHORT).show();
                    // enterAadharEt.setEnabled(false);
                } else {*/
                // enterAadharEt.setEnabled(true);
                // aadharUid = s.toString();
                if (s.toString().length() == 12) {
                    /**check enter aadhar card exist in our local database or not**/
                    if (new AddShgMemberActivity().aadharExistInDb(s.toString().trim())) {
                        getLocation();
                        aadharScannerIV.setVisibility(View.VISIBLE);
                    } else {
                        enterAadharEt.setText("");
                        DialogFactory.getInstance().showAlertDialog(context, 0, context.getString(R.string.info)
                                , context.getString(R.string.aadhar_card_exist), context.getString(R.string.ok)
                                , new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(@NonNull DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }, null, null, false);
                        //  Toast.makeText(AadharAccountActivity.this, "Aadhar Card Exist!!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    aadharScannerIV.setVisibility(View.GONE);
                }

            }
        });

        aadharGenderEt.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        InputFilter inputFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; ++i) {
                    if (!Pattern.compile("[MFT]*").matcher(String.valueOf(source.charAt(i))).matches()) {
                        aadharGenderEt.setText("");
                        aadharGenderEt.setError(getString(R.string.error_gender));
                        return "";
                    }
                }
                return null;
            }
        };
        aadharGenderEt.setFilters(new InputFilter[]{inputFilter, new InputFilter.LengthFilter(1)});


        aadharMobileNumberEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(@NonNull Editable s) {
                if (s.toString().length() == 10) {
                    if (AppUtils.isValid(s.toString())) {
                        Toast.makeText(AadharAccountActivity.this, context.getString(R.string.valid), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AadharAccountActivity.this, context.getString(R.string.not_valid), Toast.LENGTH_SHORT).show();
                        aadharMobileNumberEt.setText("");
                        aadharMobileNumber = "";
                    }
                }
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        GpsTracker gpsTracker = new GpsTracker(AadharAccountActivity.this);
        if (!AppUtils.isGPSEnabled(AadharAccountActivity.this)) {
            DialogFactory.getInstance().showAlertDialog(AadharAccountActivity.this,
                    R.drawable.ic_launcher_background, getString(R.string.app_name),
                    getString(R.string.gpsMsg),
                    getString(R.string.gotoSetting), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    }, "", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }, false);
        } else {
            gpsTracker.getLocation();
            latitude = String.valueOf(gpsTracker.latitude);
            longitude = String.valueOf(gpsTracker.longitude);
        }
    }

    public void getCoordinator() {
        GpsTracker gpsTracker = new GpsTracker(AadharAccountActivity.this);
        if (!AppUtils.isGPSEnabled(AadharAccountActivity.this)) {
            DialogFactory.getInstance().showAlertDialog(AadharAccountActivity.this,
                    R.drawable.ic_launcher_background, getString(R.string.app_name),
                    getString(R.string.gpsMsg),
                    getString(R.string.gotoSetting), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    }, "", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }, false);
        } else {
            gpsTracker.getLocation();
            latitude = String.valueOf(gpsTracker.latitude);
            longitude = String.valueOf(gpsTracker.longitude);
        }
    }

    @NonNull
    private String getLocation() {
        String blockName = SplashScreen.getInstance().getDaoSession().getBlockDaoDataDao().queryBuilder()
                .where(BlockDaoDataDao.Properties.BlockCode.eq(blockCode)).limit(1).unique().getBlockname();
        String gpName = SplashScreen.getInstance().getDaoSession().getGpsDataDao().queryBuilder()
                .where(GpsDataDao.Properties.GpCode.eq(gpCode)).limit(1).unique().getGpName();
        String villageName = SplashScreen.getInstance().getDaoSession().getVillageDataDao().queryBuilder()
                .where(VillageDataDao.Properties.VillageCode.eq(villageCode)).limit(1).unique().getVillageName();
        return blockName + ">>" + gpName + ">>" + villageName;
    }

    private String getShgMemberName() {
        String shgMemberName = SplashScreen.getInstance().getDaoSession().getShgMemberDetailsDataDao().queryBuilder()
                .where(ShgMemberDetailsDataDao.Properties.ShgMemberCode.eq(shgMemberCode)).limit(1).unique().getShgMemberName();
        return shgMemberName;
    }

    private String getShgName() {
        String shgName = SplashScreen.getInstance().getDaoSession().getShgDetailDataDao().queryBuilder()
                .where(ShgDetailDataDao.Properties.ShgCode.eq(shgCode)).limit(1).unique().getShgGroupName();
        return shgName;
    }

    private void SetListner() {
       /* spinner_BankTypeSpin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clearFocus(1);
                bankTypeNam = spinner_BankTypeSpin.getText().toString();
                bankTypeCode = getBankTypeCode(bankTypeNam);
                fillBankNameList();
                bindBankData();
            }
        });*/
        spinner_BankNameSpin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clearFocus(2);
                bankName = spinner_BankNameSpin.getText().toString();
                bankNameCode = getBankCode(bankName);
                bankAccLength = getBankAccLength(bankNameCode);
                fillbankBranchesData();
                bindBankBranchdata();
            }
        });
        spinner_BankBranchSpin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bankBranchName = spinner_BankBranchSpin.getText().toString();
                bankBranchCode = getBankBranchCode(bankBranchName);
                ifscCode = getIfscCode(bankBranchCode);
                accountDetailLinearLayout.setVisibility(View.VISIBLE);
                ifscTextView.setText(ifscCode);
                getCoordinator();
                //write logic for account length
                //set the edittext length runtime
                if (bankAccLength.equalsIgnoreCase("Bank A/c length N/A !!!")) {
                    accNumberTIL.setHelperText(getString(R.string.enterValidAccMsg));
                } else {
                    accountLength = Integer.parseInt(bankAccLength);
                    setAcclength();
                    accNumberTIL.setHelperText(getString(R.string.enter) + bankAccLength + getString(R.string.enterDigitMsg));
                    accNumberTIL.setCounterEnabled(true);
                    accNumberTIL.setCounterMaxLength(accountLength);
                }

                accountNumberEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            accountNumberEt.setInputType(InputType.TYPE_CLASS_NUMBER |
                                    InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        } else {
                            accountNumberEt.setInputType(InputType.TYPE_CLASS_NUMBER);
                            accountNumberEt.setSelection(accountNumberEt.getText().toString().length());
                        }
                    }
                });
                accountNumberEt.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (accountNumberEt.getText().toString().length() < 6) {
                            accountNumberEt.setError(getString(R.string.error_acc_length));
                        } else {
                            if (confirmAccountNumberEt.getText().toString() != null) {
                                if (accountNumberEt.getText().toString().length() == confirmAccountNumberEt.getText().toString().length()) {
                                    if (!confirmAccountNumberEt.getText().toString().equalsIgnoreCase(accountNumberEt.getText().toString())) {
                                        confirmAccountNumberEt.setError(getString(R.string.accountNoIsWrong));
                                        submitAccountBtn.setEnabled(false);
                                        submitAccountBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                                    } else {
                                        confirmAccountNumberEt.setError("");
                                        submitAccountBtn.setEnabled(true);
                                        submitAccountBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                                    }
                                }
                            }

                        }
                    }
                });
                confirmAccountNumberEt.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String accNum = accountNumberEt.getText().toString().trim();
                        if (confirmAccountNumberEt.getText().toString().length() == accNum.length()) {
                            if (!confirmAccountNumberEt.getText().toString().equalsIgnoreCase(accountNumberEt.getText().toString())) {
                                confirmAccountNumberEt.setError(getString(R.string.accountNoIsWrong));
                                submitAccountBtn.setEnabled(false);
                                submitAccountBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                            } else {
                                confirmAccountNumberEt.setError(null);
                                submitAccountBtn.setEnabled(true);
                                submitAccountBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                            }
                        }
                    }
                });
            }
        });

    }

    private void setAcclength() {
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(accountLength);
        accountNumberEt.setFilters(fArray);
        confirmAccountNumberEt.setFilters(fArray);
    }

    private String getBankAccLength(String bankNameCode) {
        String length = "";
        length = SplashScreen.getInstance().getDaoSession().getBankDetailDataDao().queryBuilder()
                .where(BankDetailDataDao.Properties.BankCode.eq(bankNameCode)).limit(1).unique().getBankAccStatus();
        return length;
    }

    private String getIfscCode(String bankBranchCode) {
        String ifsc = "";
        ifsc = SplashScreen.getInstance().getDaoSession().getBankBranchDetailDataDao().queryBuilder()
                .where(BankBranchDetailDataDao.Properties.BankBranchCode.eq(bankBranchCode)).limit(1).unique().getIfscCode();
        return ifsc;
    }


    private String getBankBranchCode(@NonNull String bankBranchName) {
        String bankBranchCode = "";
        for (int i = 0; i < bankBranchesDataListItems.size(); i++) {
            if (bankBranchName.equalsIgnoreCase(bankBranchesDataListItems.get(i).getBankBranchName())) {
                bankBranchCode = bankBranchesDataListItems.get(i).getBankBranchCode();
            }
        }
        return bankBranchCode;
    }

    private void bindBankBranchdata() {
        bankBranchArray = new String[bankBranchesDataListItems.size()];
        for (int i = 0; i < bankBranchesDataListItems.size(); i++) {
            bankBranchArray[i] = bankBranchesDataListItems.get(i).getBankBranchName();
        }
        bankBranchNameArrayAdapter = new ArrayAdapter<String>(AadharAccountActivity.this,
                R.layout.spinner_textview, bankBranchArray);
        spinner_BankBranchSpin.setAdapter(bankBranchNameArrayAdapter);
    }

    private void fillbankBranchesData() {
        bankBranchesDataListItems = SplashScreen.getInstance().getDaoSession().getBankBranchDetailDataDao()
                .queryBuilder().where(BankBranchDetailDataDao.Properties.BankCode.eq(bankNameCode)).build().list();

    }

    private String getBankCode(@NonNull String bankName) {
        String bankCode = "";
        for (int i = 0; i < bankNameDataListItems.size(); i++) {
            if (bankName.equalsIgnoreCase(bankNameDataListItems.get(i).getBankName())) {
                bankCode = bankNameDataListItems.get(i).getBankCode();
            }
        }
        return bankCode;
    }

    private void bindBankData() {
        bankNameArray = new String[bankNameDataListItems.size()];
        for (int i = 0; i < bankNameDataListItems.size(); i++) {
            bankNameArray[i] = bankNameDataListItems.get(i).getBankName();
        }
        bankNameArrayAdapter = new ArrayAdapter<String>(AadharAccountActivity.this,
                R.layout.spinner_textview, bankNameArray);
        spinner_BankNameSpin.setAdapter(bankNameArrayAdapter);
    }

    private void fillBankNameList() {
        bankNameDataListItems = SplashScreen.getInstance().getDaoSession().getBankDetailDataDao()
                .queryBuilder().where(BankDetailDataDao.Properties.BankTypeCode.eq(bankTypeCode)).build().list();

    }

    private String getBankTypeCode(@NonNull String bankTypeName) {
        String bankTypeCode = "";
        for (int i = 0; i < bankTypeDataListItems.size(); i++) {
            if (bankTypeName.equalsIgnoreCase(bankTypeDataListItems.get(i).getBankType())) {
                bankTypeCode = bankTypeDataListItems.get(i).getBankTypeCode();
            }
        }
        return bankTypeCode;
    }

    private void initilaizAllList() {
        bankTypeDataListItems = new ArrayList<>();
        bankNameDataListItems = new ArrayList<>();
        bankBranchesDataListItems = new ArrayList<>();
    }

    private void bindBankTypeData() {

        bankNameDataListItems = SplashScreen.getInstance().getDaoSession().getBankDetailDataDao()
                .queryBuilder().build().list();

        bankNameArray = new String[bankNameDataListItems.size()];
        for (int i = 0; i < bankNameDataListItems.size(); i++) {
            bankNameArray[i] = bankNameDataListItems.get(i).getBankName();
        }
        bankNameArrayAdapter = new ArrayAdapter<String>(AadharAccountActivity.this,
                R.layout.spinner_textview, bankNameArray);
        spinner_BankNameSpin.setAdapter(bankNameArrayAdapter);


       /* bankTypeDataListItems = SplashScreen.getInstance().getDaoSession()
                .getBankTypeDao().queryBuilder().build().list();
        bankTypeArray = new String[bankTypeDataListItems.size()];
        for (int i = 0; i < bankTypeDataListItems.size(); i++) {
            bankTypeArray[i] = bankTypeDataListItems.get(i).getBankType();
        }
        bankTypeArrayAdapter = new ArrayAdapter<String>(AadharAccountActivity.this,
                R.layout.spinner_textview, bankTypeArray);
        spinner_BankTypeSpin.setAdapter(bankTypeArrayAdapter);*/
    }

    private void clearFocus(int i) {
        switch (i) {
            case 1:
                accountDetailLinearLayout.setVisibility(View.GONE);

                bankNameCode = "";
                bankBranchCode = "";
                bankName = "";
                bankBranchName = "";
                ifscCode = "";
                ifscTextView.setText(ifscCode);

                spinner_BankNameSpin.setFocusableInTouchMode(false);
                spinner_BankBranchSpin.setFocusableInTouchMode(false);

                bankNameArray = new String[0];
                bankBranchArray = new String[0];


                bankNameDataListItems.clear();
                bankBranchesDataListItems.clear();

                spinner_BankNameSpin.setText("");
                spinner_BankBranchSpin.setText("");

                ArrayAdapter<String> bankNameAdapter = new ArrayAdapter<String>(AadharAccountActivity.this,
                        R.layout.spinner_textview, bankNameArray);
                spinner_BankNameSpin.setAdapter(bankNameAdapter);

                ArrayAdapter<String> bankBranchNameAdapter = new ArrayAdapter<String>(AadharAccountActivity.this,
                        R.layout.spinner_textview, bankBranchArray);
                spinner_BankBranchSpin.setAdapter(bankBranchNameAdapter);

                break;
            case 2:
                //remove data on image and other edit text
                accountDetailLinearLayout.setVisibility(View.GONE);
                bankBranchCode = "";

                bankBranchName = "";
                ifscCode = "";
                ifscTextView.setText(ifscCode);

                accNumber = "";
                confirmAccNumber = "";
                bankAccLength = "";
                longitude = "";
                latitude = "";

                accountNumberEt.setText("");

                accNumberTIL.setHelperText("");
                accNumberTIL.setCounterEnabled(false);
                accNumberTIL.setCounterMaxLength(0);
                confirmAccountNumberEt.setText("");

                spinner_BankBranchSpin.setFocusableInTouchMode(false);

                bankBranchArray = new String[0];

                bankBranchesDataListItems.clear();

                spinner_BankBranchSpin.setText("");

                ArrayAdapter<String> bankBranchNameAdapter1 = new ArrayAdapter<String>(AadharAccountActivity.this,
                        R.layout.spinner_textview, bankBranchArray);
                spinner_BankBranchSpin.setAdapter(bankBranchNameAdapter1);
        }
    }


    @OnClick(R.id.aadharScannerIV1)
    void imageView() {
        IntentIntegrator integrator = new IntentIntegrator(AadharAccountActivity.this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setPrompt(getString(R.string.scanBarCode));
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
    }


    @OnClick(R.id.takAadhaarSelfiBtn)
    void takeSelfiPhoto() {
        aadharUid = enterAadharEt.getText().toString().trim();
        if (aadharUid != null && !aadharUid.isEmpty()) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,
                    CAPTURE_IMAGE_ACTIVITY_SELFI_REQUEST_CODE);
        } else {
            Toast.makeText(AadharAccountActivity.this, getString(R.string.fillAddharDetail), Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick(R.id.takeAgainSelfiBtn)
    void takeSelfiAgainPhoto() {
        aadharUid = enterAadharEt.getText().toString().trim();
        if (aadharUid != null && !aadharUid.isEmpty()) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,
                    CAPTURE_IMAGE_ACTIVITY_SELFI_REQUEST_CODE);
        } else {
            Toast.makeText(AadharAccountActivity.this, getString(R.string.fillAddharDetail), Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick(R.id.takePhotoBtn)
    void takePaasbookPhoto() {
        accNumber = accountNumberEt.getText().toString().trim();
        confirmAccNumber = confirmAccountNumberEt.getText().toString().trim();
        if ((accNumber != null && !accNumber.isEmpty()) && (confirmAccNumber != null && !confirmAccNumber.isEmpty())) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,
                    CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        } else {
            Toast.makeText(AadharAccountActivity.this, getString(R.string.fillAccountDetail), Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick(R.id.takeAgainBtn)
    void takeAgainPaasbookPhoto() {
        accNumber = accountNumberEt.getText().toString().trim();
        confirmAccNumber = confirmAccountNumberEt.getText().toString().trim();
        if ((accNumber != null && !accNumber.isEmpty()) && (confirmAccNumber != null && !confirmAccNumber.isEmpty())) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,
                    CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        } else {
            Toast.makeText(AadharAccountActivity.this, getString(R.string.fillAccountDetail), Toast.LENGTH_SHORT).show();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.submitAadharBtn)
    void submitAadharDetail() {
        /*ProgressDialog progressDialog=new ProgressDialog(AadharAccountActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);*/
        if (isSyncable) {
            getCoordinator();
            aadharUid = enterAadharEt.getText().toString().trim();
            aadharMobileNumber = aadharMobileNumberEt.getText().toString().trim();
            if (aadharMobileNumber.length() < 10) {
                aadharMobileNumberEt.setError(getString(R.string.enterValidMobile));
            } else if (aadharMobileNumber != null && aadharMobileNumber.isEmpty()) {
                aadharMobileNumberEt.setError(getString(R.string.enterValidMobile));
                aadharMobileNumberEt.setFocusable(true);
                aadharMobileNumberEt.setEnabled(true);
            } else {
                if (cardScanStatus.trim().equalsIgnoreCase("0")) {
                    name = enterAadhaeNameET.getText().toString().trim();
                    gender = aadharGenderEt.getText().toString().trim();
                    co = aadharFatherNameEt.getText().toString().trim();
                    yob = aadharYobEt.getText().toString().trim();
                    aadharAddress = aadharAddressEt.getText().toString().trim();
                    if (name.isEmpty() || name.equalsIgnoreCase(""))
                        enterAadhaeNameET.setError(getString(R.string.error_name));
                    else if (gender.isEmpty() || gender.equalsIgnoreCase(""))
                        aadharGenderEt.setError(getString(R.string.error_gender));
                    else if (co.isEmpty() || co.equalsIgnoreCase(""))
                        aadharFatherNameEt.setError(getString(R.string.error_Fname));
                    else if (yob.isEmpty() || yob.equalsIgnoreCase(""))
                        aadharYobEt.setError(getString(R.string.error_yob));
                    else if (aadharAddress.isEmpty() || aadharAddress.equalsIgnoreCase(""))
                        aadharAddressEt.setError(getString(R.string.error_address));

                    else if (aadharUid.isEmpty() || aadharUid.equalsIgnoreCase(""))
                        enterAadharEt.setError(getString(R.string.error_uidai_id));

                    doFuckingThink();
                } else {
                    name = enterAadhaeNameET.getText().toString().trim();
                    gender = aadharGenderEt.getText().toString().trim();
                    co = aadharFatherNameEt.getText().toString().trim();
                    yob = aadharYobEt.getText().toString().trim();
                    aadharAddress = aadharAddressEt.getText().toString().trim();
                    if (name.isEmpty() || name.equalsIgnoreCase(""))
                        enterAadhaeNameET.setError(getString(R.string.error_name));
                    else if (gender.isEmpty() || gender.equalsIgnoreCase(""))
                        aadharGenderEt.setError(getString(R.string.error_gender));
                    else if (co.isEmpty() || co.equalsIgnoreCase(""))
                        aadharFatherNameEt.setError(getString(R.string.error_Fname));
                    else if (yob.isEmpty() || yob.equalsIgnoreCase(""))
                        aadharYobEt.setError(getString(R.string.error_yob));
                    else if (aadharAddress.isEmpty() || aadharAddress.equalsIgnoreCase(""))
                        aadharAddressEt.setError(getString(R.string.error_address));

                    else if (aadharUid.isEmpty() || aadharUid.equalsIgnoreCase(""))
                        enterAadharEt.setError(getString(R.string.error_uidai_id));
                    else
                        doFuckingThink();
                }
            }
        } else {
            DialogFactory.getInstance().showAlertDialog(AadharAccountActivity.this, 0,
                    getString(R.string.info), getString(R.string.toast_images_msg),
                    getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(@NonNull DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }, true);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void doFuckingThink() {
        int dataVerficationCount = 0;

        if (pdfByteArray != null) {
            if (aadharUid != null && !aadharUid.isEmpty()) {
                if (imageSelfiByteArray != null) {
                    if (!latitude.equalsIgnoreCase("0.0") && !longitude.equalsIgnoreCase("0.0")) {

                        ShgMemberDetailsData shgMemberDetailsData = SplashScreen.getInstance().getDaoSession().getShgMemberDetailsDataDao()
                                .queryBuilder().where(ShgMemberDetailsDataDao.Properties.ShgMemberCode.eq(shgMemberCode)).limit(1).unique();
                        AppUtils.getInstance().showLog("MNAME=" + shgMemberDetailsData.getShgMemberName().trim().toLowerCase()
                                + ",MbelongingName=" + shgMemberDetailsData.getBelonging_name().trim().toLowerCase() + ",Mgender=" +
                                shgMemberDetailsData.getGender().trim().toLowerCase() + "MDOB=" + shgMemberDetailsData.getDob().trim(), AadharAccountActivity.class);

                        if (shgMemberDetailsData.getShgMemberName().trim().toLowerCase().equalsIgnoreCase(name.toLowerCase())) {
                            dataVerficationCount += 1;
                        }
                        if (shgMemberDetailsData.getBelonging_name().trim().toLowerCase().equalsIgnoreCase(co.toLowerCase())) {
                            dataVerficationCount += 1;
                        }
                        if (shgMemberDetailsData.getGender().trim().toLowerCase().equalsIgnoreCase(gender.toLowerCase())) {
                            dataVerficationCount += 1;
                        }
                        AppUtils.getInstance().showLog("yobforcondition" + yob, AadharAccountActivity.class);
                        if (yob.trim().length() > 4) {
                            if (shgMemberDetailsData.getDob().trim().equalsIgnoreCase(yob)) {
                                dataVerficationCount += 1;
                            }
                        } else {
                            if (shgMemberDetailsData.getDob().trim() != null && shgMemberDetailsData.getDob().trim().length() > 0) {
                                String memberYob = shgMemberDetailsData.getDob().trim().substring(6, shgMemberDetailsData.getDob().trim().length() - 1);
                                AppUtils.getInstance().showLog("memberYob=" + memberYob, AadharAccountActivity.class);
                                if (memberYob.trim().equalsIgnoreCase(yob.trim())) {
                                    dataVerficationCount += 1;
                                }
                            }
                        }

                        if (dataVerficationCount < 2) {
                            DialogFactory.getInstance().showAlertDialog(AadharAccountActivity.this, 0,
                                    getString(R.string.info), getString(R.string.error_aadhar_details_wrong),
                                    getString(R.string.ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(@NonNull DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }, true);
                        } else {

                            AddMemberAadharDetail addMemberAadharDetail = new AddMemberAadharDetail();
                            addMemberAadharDetail.setUidNumber(aadharUid);
                            SplashScreen.getInstance().getDaoSession().getAddMemberAadharDetailDao().insert(addMemberAadharDetail);
                            //progressDialog.show();
                            AadharDetailSyncData aadharDetailSyncData = new AadharDetailSyncData();
                            aadharDetailSyncData.setVillageCode(villageCode);
                            aadharDetailSyncData.setShgCode(shgCode);
                            aadharDetailSyncData.setShgMemberCode(shgMemberCode);
                            aadharDetailSyncData.setAadharNumber(aadharUid);
                            aadharDetailSyncData.setAadharName(name);
                            aadharDetailSyncData.setAadharYOB(yob);
                            aadharDetailSyncData.setAadharGender(gender);
                            aadharDetailSyncData.setConcentForm(pdfByteArray);
                            aadharDetailSyncData.setAadhaarMobileNumber(aadharMobileNumber);
                            aadharAddress = aadharAddressEt.getText().toString().trim();
                            AppUtils.getInstance().showLog("aadharAddress" + aadharAddress, AadharAccountActivity.class);
                            //  aadharAddress = house + ", " + loc + ", " + vtc + ", " + dist + ", " + state + ", " + pc;
                            aadharDetailSyncData.setAadharAddress(aadharAddress);
                            aadharDetailSyncData.setPostalCode("NO");
                            aadharDetailSyncData.setAadharFatherName(co);
                            aadharDetailSyncData.setAadharSyncStatus("0");
                            aadharDetailSyncData.setScanStatus(scanStatus);
                            aadharDetailSyncData.setLatLong(latitude + "," + longitude);
                            if (imageSelfiByteArray != null)
                                aadharDetailSyncData.setAadhaarImage(imageSelfiByteArray);
                            else {
                                byte[] blank = "blank".getBytes();
                                aadharDetailSyncData.setAadhaarImage(blank);
                            }

                            SplashScreen.getInstance().getDaoSession().getAadharDetailSyncDataDao().insert(aadharDetailSyncData);
                            //set status in master tables for acc is saved sucessfully
                            ShgMemberDetailsData shgMemberDetailsData1 = SplashScreen.getInstance().getDaoSession().getShgMemberDetailsDataDao()
                                    .queryBuilder().where(ShgMemberDetailsDataDao.Properties.ShgMemberCode.eq(shgMemberCode)).limit(1).unique();

                            shgMemberDetailsData1.setMemAadharCurrentStatus("P");
                            SplashScreen.getInstance().getDaoSession().getShgMemberDetailsDataDao().update(shgMemberDetailsData1);

                            if (AppUtils.isInternetOn(AadharAccountActivity.this)) {
                                PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getCallingApi(), AppConstant.CALLING_API_AADHAR, AadharAccountActivity.this);
                                ProgressDialog progressDialog = new ProgressDialog(AadharAccountActivity.this);
                                progressDialog.setMessage(getResources().getString(R.string.syncing_data));
                                progressDialog.setCancelable(false);
                                progressDialog.show();
                                PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getCallingApi(), AppConstant.CALLING_API_AADHAR, AadharAccountActivity.this);
                                SyncData.getInstance().syncData(AadharAccountActivity.this);

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressDialog.dismiss();
                                        String aadharSyncVolleyError = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getAadharApiVolleyError(), AadharAccountActivity.this);
                                        if (AppConstant.AADHAR_API_VOLLEY_ERROR.equalsIgnoreCase(aadharSyncVolleyError)) {
                                            PreferenceFactory.getInstance().removeSharedPrefrencesData(PreferenceManager.getAadharApiVolleyError(), AadharAccountActivity.this);
                                            DialogFactory.getInstance().showAlertDialog(AadharAccountActivity.this, 0, getString(R.string.info), getString(R.string.AADHAR_API_VOLLEY_ERROR),
                                                    getString(R.string.ok), new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(@NonNull DialogInterface dialog, int which) {
                                                            dialog.dismiss();
                                                            PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getPrefKeyShgMemberStatus()
                                                                    , "goToMemberList", AadharAccountActivity.this);
                                                            Intent intent = new Intent(AadharAccountActivity.this, HomeActivity.class);
                                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                            startActivity(intent);
                                                        }
                                                    }, false);
                                        } else {
                                            aadharDetailSync("sync");
                                        }
                                    }
                                }, 10000);


                            } else {
                                try {
                                    updateAadharBackupFile(context);
                                    aadharDetailSync("save");

                                } catch (NoSuchPaddingException e) {
                                    e.printStackTrace();
                                } catch (NoSuchAlgorithmException e) {
                                    e.printStackTrace();
                                } catch (IllegalBlockSizeException e) {
                                    e.printStackTrace();
                                } catch (BadPaddingException e) {
                                    e.printStackTrace();
                                } catch (InvalidAlgorithmParameterException e) {
                                    e.printStackTrace();
                                } catch (InvalidKeyException e) {
                                    e.printStackTrace();
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(AadharAccountActivity.this, getString(R.string.data_save_msg), Toast.LENGTH_SHORT).show();

                            }
                        }
                    } else {
                        Toast.makeText(AadharAccountActivity.this, getString(R.string.wait_for_adding_location), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(AadharAccountActivity.this, getString(R.string.take_member_photo), Toast.LENGTH_SHORT).show();

                }
            } else {
                Toast.makeText(AadharAccountActivity.this, getString(R.string.fillAccountDetail), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(AadharAccountActivity.this, getString(R.string.error_uploade_concent), Toast.LENGTH_SHORT).show();
        }
    }


    private void aadharDetailSync(@NonNull String saveStatus) {

        //   Toast.makeText(AadharAccountActivity.this,getString(R.string.data_sync_msg),Toast.LENGTH_SHORT).show();

        Button ok;
        ImageView imageView;
        TextView data_save_statusTV;
        Dialog dialog = new Dialog(AadharAccountActivity.this);
        dialog.setContentView(R.layout.my_confirmation_dialog);
        ok = (Button) dialog.findViewById(R.id.buttonOk);
        data_save_statusTV = (TextView) dialog.findViewById(R.id.data_save_statusTV);
        if (saveStatus.contentEquals("sync"))
            data_save_statusTV.setText(R.string.data_sync_msg);
        else
            data_save_statusTV.setText(R.string.data_save_msg);
        imageView = (ImageView) dialog.findViewById(R.id.checkImageView);
        dialog.show();
        dialog.setCancelable(false);
        Glide.with(getApplicationContext()).asGif().load(R.drawable.green_check).into(imageView);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (bankAccountLinkStatus.equalsIgnoreCase("0")) {
                    accDetailCardView.setVisibility(View.VISIBLE);
                    bankDetailsHeaderCV.setVisibility(View.VISIBLE);
                    /*show text for aadhar is linked*/
                    aadhar_link_status.setVisibility(View.VISIBLE);
                    aadharDetailCardView.setVisibility(View.GONE);

                    initilaizAllList();
                    // spinner_BankNameSpin.setFocusableInTouchMode(false);
                    spinner_BankBranchSpin.setFocusableInTouchMode(false);
                    bindBankTypeData();
                    SetListner();
                } else {
                    PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getPrefKeyShgMemberStatus()
                            , "goToMemberList", AadharAccountActivity.this);
                    Intent intent = new Intent(AadharAccountActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void updateAadharBackupFile(@NonNull Context context) throws NoSuchPaddingException, NoSuchAlgorithmException
            , IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException
            , InvalidKeyException, UnsupportedEncodingException {

        SyncData syncData = SyncData.getInstance();
        syncData.getLogInfo(context);

        FileUtility.getInstance().createFileInMemory(FileManager.getInstance()
                        .getPathDetails(context)
                , AppConstant.AADHAR_FILE_NAME, new Cryptography().encrypt(syncData.aaDharSyncJsonObject(context).toString()));
    }

    public List<LoginInfoData> getLoginInfo() {
        return SplashScreen.getInstance().getDaoSession().getLoginInfoDataDao().queryBuilder().build().list();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.submitAccountBtn)
    void SubmitAccountDetail() {
        accNumber = accountNumberEt.getText().toString().trim();
        confirmAccNumber = confirmAccountNumberEt.getText().toString().trim();
        if (bankBranchName.trim().isEmpty()) {
            spinner_BankBranchSpin.setError(getString(R.string.error_bank_branch));
        } else {
            if (!bankAccLength.equalsIgnoreCase("Bank A/c length N/A !!!")) {
                if ((accNumber != null && !accNumber.isEmpty()) && (confirmAccNumber != null && !confirmAccNumber.isEmpty())) {
                    if (confirmAccNumber.length() == Integer.parseInt(bankAccLength)) {
                        submitAccountData();
                    } else {
                        Toast.makeText(AadharAccountActivity.this, getString(R.string.invalid_acc_no), Toast.LENGTH_SHORT).show();
                    }
                }

            } else {
                submitAccountData();
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void submitAccountData() {
        if (isSyncable) {
            if (!latitude.equalsIgnoreCase("0.0") && !longitude.equalsIgnoreCase("0.0")) {
                if ((accNumber != null && !accNumber.isEmpty()) && (confirmAccNumber != null && !confirmAccNumber.isEmpty())) {
                    if (imageByteArray != null) {
                        Toast.makeText(AadharAccountActivity.this, latitude + "," + longitude, Toast.LENGTH_LONG).show();
                        AccountDetailSyncData accountDetailSyncData = new AccountDetailSyncData();
                        accountDetailSyncData.setAccountNumber(confirmAccNumber);
                        accountDetailSyncData.setVillageCode(villageCode);
                        accountDetailSyncData.setShgCode(shgCode);
                        accountDetailSyncData.setShgMemberCode(shgMemberCode);
                        accountDetailSyncData.setBankBranchCode(bankBranchCode);
                        accountDetailSyncData.setBankNameCode(bankNameCode);
                        accountDetailSyncData.setIfscNumber(ifscCode);
                        accountDetailSyncData.setLatLong(latitude + "," + longitude);
                        accountDetailSyncData.setSyncStatus("0");
                        accountDetailSyncData.setAutoIncrement(null);

                        if (imageByteArray != null)
                            accountDetailSyncData.setPhotoImage(imageByteArray);
                        else {
                            byte[] blank = "blanck".getBytes();
                            accountDetailSyncData.setPhotoImage(blank);
                        }
                        SplashScreen.getInstance().getDaoSession().getAccountDetailSyncDataDao().insert(accountDetailSyncData);

                        //set status in master tables for acc is saved sucessfully
                        ShgMemberDetailsData shgMemberDetailsData = SplashScreen.getInstance().getDaoSession().getShgMemberDetailsDataDao()
                                .queryBuilder().where(ShgMemberDetailsDataDao.Properties.ShgMemberCode.eq(shgMemberCode)).limit(1).unique();
                        shgMemberDetailsData.setMemBankAccCurrentStatus("P");

                        SplashScreen.getInstance().getDaoSession().getShgMemberDetailsDataDao().update(shgMemberDetailsData);
                        if (AppUtils.isInternetOn(AadharAccountActivity.this)) {
                            PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getCallingApi(), AppConstant.CALLING_API_BANK, AadharAccountActivity.this);
                            ProgressDialog progressDialog = new ProgressDialog(AadharAccountActivity.this);
                            progressDialog.setMessage(getResources().getString(R.string.syncing_data));
                            progressDialog.setCancelable(false);
                            progressDialog.show();
                     /*   syncBankDataOnServer(progressDialog,getApplicationContext());
                        bankAccountSyncStatus();*/
                            PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getCallingApi(), AppConstant.CALLING_API_BANK, AadharAccountActivity.this);
                            SyncData.getInstance().syncData(AadharAccountActivity.this);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                    String bankAccountSyncVolleyError = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getBankAccApiVolleyError(), AadharAccountActivity.this);
                                    if (AppConstant.BANK_ACC_API_VOLLEY_ERROR.equalsIgnoreCase(bankAccountSyncVolleyError)) {
                                        PreferenceFactory.getInstance().removeSharedPrefrencesData(PreferenceManager.getBankAccApiVolleyError(), AadharAccountActivity.this);
                                        DialogFactory.getInstance().showAlertDialog(AadharAccountActivity.this, 0, getString(R.string.info), getString(R.string.BANK_ACC_API_VOLLEY_ERROR),
                                                getString(R.string.ok), new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(@NonNull DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                        PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getPrefKeyShgMemberStatus()
                                                                , "goToMemberList", AadharAccountActivity.this);
                                                        Intent intent = new Intent(AadharAccountActivity.this, AadharAccountActivity.class);
                                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        startActivity(intent);
                                                    }
                                                }, false);

                                    } else
                                        bankAccountSyncStatus("sync");
                                }
                            }, 3000);
                        } else {
                            try {

                                updateBankAccountBackupFile(AadharAccountActivity.this);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        bankAccountSyncStatus("save");
                                    }
                                }, 1000);
                            } catch (NoSuchPaddingException e) {
                                e.printStackTrace();
                            } catch (NoSuchAlgorithmException e) {
                                e.printStackTrace();
                            } catch (IllegalBlockSizeException e) {
                                e.printStackTrace();
                            } catch (BadPaddingException e) {
                                e.printStackTrace();
                            } catch (InvalidAlgorithmParameterException e) {
                                e.printStackTrace();
                            } catch (InvalidKeyException e) {
                                e.printStackTrace();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(AadharAccountActivity.this, getString(R.string.data_save_msg), Toast.LENGTH_SHORT).show();
                        }

                        //show dilog

                    } else {
                        Toast.makeText(AadharAccountActivity.this, getString(R.string.takePassbookPhoto), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AadharAccountActivity.this, getString(R.string.fillAccountDetail), Toast.LENGTH_LONG).show();
                }

            } else {
                getCoordinator();
                Toast.makeText(AadharAccountActivity.this, getString(R.string.getting_location), Toast.LENGTH_SHORT).show();
            }
        } else {
            DialogFactory.getInstance().showAlertDialog(AadharAccountActivity.this, 0,
                    getString(R.string.info), getString(R.string.toast_images_msg),
                    getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(@NonNull DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }, true);
        }

    }


    private void bankAccountSyncStatus(@NonNull String dataStatus) {
        Button ok;
        ImageView imageView;
        TextView data_save_statusTV;
        Dialog dialog = new Dialog(AadharAccountActivity.this);
        dialog.setContentView(R.layout.my_confirmation_dialog);
        ok = (Button) dialog.findViewById(R.id.buttonOk);
        data_save_statusTV = (TextView) dialog.findViewById(R.id.data_save_statusTV);
        imageView = (ImageView) dialog.findViewById(R.id.checkImageView);
        if (dataStatus.contentEquals("sync"))
            data_save_statusTV.setText(R.string.data_sync_msg);
        else
            data_save_statusTV.setText(R.string.data_save_msg);
        dialog.show();
        dialog.setCancelable(false);
        //Glide.with(getApplicationContext()).asGif().load(R.drawable.green_check).into(imageView);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getPrefKeyShgMemberStatus()
                        , "goToMemberList", AadharAccountActivity.this);
                Intent intent = new Intent(AadharAccountActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void updateBankAccountBackupFile(@NonNull Context context) throws NoSuchPaddingException, NoSuchAlgorithmException
            , IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException
            , InvalidKeyException, UnsupportedEncodingException {

        SyncData syncData = SyncData.getInstance();
        syncData.getLogInfo(context);

        FileUtility.getInstance().createFileInMemory(FileManager.getInstance()
                        .getPathDetails(context)
                , AppConstant.BANK_ACCOUNT_FILE_NAME, new Cryptography().encrypt(syncData.accountSyncJsonObject(context).toString()));

    }


    public String getBlockCode() {
        String bCode = SplashScreen.getInstance().getDaoSession().getBlockDaoDataDao()
                .queryBuilder().limit(1).build().unique().getBlockCode();
        return bCode;
    }

    @Nullable
    public String getLoginId(@NonNull Context context) {
        String loginId = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getPrefLoginId(), context);
        return loginId;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                photoLinearLayout.setVisibility(View.VISIBLE);
                takePhotoBtn.setVisibility(View.GONE);
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bmp = (Bitmap) data.getExtras().get("data");
                        InputImage image = InputImage.fromBitmap(bmp, 0);
                        textRecognizer.process(image)
                                .addOnSuccessListener(new OnSuccessListener<Text>() {
                                    @Override
                                    public void onSuccess(Text visionText) {
                                        List<String> textLines = new ArrayList<>();

                                        for (Text.TextBlock block : visionText.getTextBlocks()) {
                                            String blockText = block.getText();
                                            AppUtils.getInstance().showLog("blockText" + blockText, AadharAccountActivity.class);
                                            for (Text.Line line : block.getLines()) {
                                                String textLine = line.getText();
                                                textLines.add(textLine);
                                                AppUtils.getInstance().showLog("lineText" + textLine, AadharAccountActivity.class);
                                                       /* String elements = "";
                                                        for (Text.Element element : line.getElements()) {
                                                            String elementText = element.getText();
                                                            elements += elementText;
                                                            AppUtils.getInstance().showLog("elementText" + elementText, AadharAccountActivity.class);

                                                        }*/
                                                //Toast.makeText(context, textLine, Toast.LENGTH_LONG).show();

                                            }
                                        }

                                        if (textLines.size() > 0) {
                                            int recognizedTextCount = 0;
                                            for (int i = 0; i < textualParameters.length; i++) {
                                                for (String textLine : textLines) {
                                                    if (textLine.toLowerCase().contains(textualParameters[i].toLowerCase())) {
                                                        recognizedTextCount += 1;
                                                    }
                                                }
                                            }

                                            AppUtils.getInstance().showLog("recognizedTextCount" + recognizedTextCount, AadharAccountActivity.class);

                                            if (recognizedTextCount > 1) {
                                                isSyncable = true;
                                            } else isSyncable = false;

                                        } else {
                                            Toast.makeText(context, "Image is not cleared", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                })
                                .addOnFailureListener(
                                        new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                AppUtils.getInstance().showLog("textrecognizingexception" + e.toString(), AadharAccountActivity.class);
                                                DialogFactory.getInstance().showAlertDialog(AadharAccountActivity.this, 0,
                                                        getString(R.string.info), getString(R.string.error_in_image),
                                                        getString(R.string.ok), new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(@NonNull DialogInterface dialog, int which) {
                                                                dialog.dismiss();
                                                            }
                                                        }, true);

                                            }
                                        });

                        /* String resultText = result.getText();*/
                        passbookImageView.post(new Runnable() {
                            @Override
                            public void run() {
                                passbookImageView.setImageBitmap(bmp);
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bmp.compress(Bitmap.CompressFormat.PNG, IMAGE_COMPRESS_QLTY, baos);
                                imageByteArray = baos.toByteArray();
                                AppUtils.getInstance().showLog("byteArray" + imageByteArray, AadharAccountActivity.class);
                            }
                        });


                    }
                });
            }

        } else if (requestCode == CAPTURE_IMAGE_ACTIVITY_SELFI_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                FaceDetector detector = FaceDetection.getClient();
                isSyncable = false;
                aadharPhotoLinearLayout.setVisibility(View.VISIBLE);
                takAadhaarSelfiBtn.setVisibility(View.GONE);
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {

                        Bitmap bmp = (Bitmap) data.getExtras().get("data");
                        InputImage image = InputImage.fromBitmap(bmp, 0);
                        aadharImageView.post(new Runnable() {
                            @Override
                            public void run() {
                                aadharImageView.setImageBitmap(bmp);
                            }
                        });
                        detector.process(image).addOnSuccessListener(new OnSuccessListener<List<Face>>() {
                            @Override
                            public void onSuccess(@NonNull List<Face> faces) {

                                if (faces.size() == 1) {
                                    isSyncable = true;
                                    AppUtils.getInstance().showLog("isSyncable" + isSyncable, AadharAccountActivity.class);
                                } else if (faces.size() > 1) {
                                    Toast.makeText(AadharAccountActivity.this, getString(R.string.toast_face_msg), Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(AadharAccountActivity.this, getString(R.string.toast_no_face_msg), Toast.LENGTH_LONG).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                DialogFactory.getInstance().showAlertDialog(AadharAccountActivity.this, 0,
                                        getString(R.string.info), getString(R.string.error_in_image),
                                        getString(R.string.ok), new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(@NonNull DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        }, true);
                            }
                        });


                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.PNG, IMAGE_COMPRESS_QLTY, baos);
                        imageSelfiByteArray = baos.toByteArray();
                        AppUtils.getInstance().showLog("byteArray" + imageSelfiByteArray, AadharAccountActivity.class);

                    }
                });

            }


        } else if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            try {
                Bitmap bmp = (Bitmap) data.getExtras().get("data");
                fileNameTV.setImageBitmap(bmp);
                concent_textTV.setVisibility(View.GONE);
                fileNameTV.setVisibility(View.VISIBLE);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, IMAGE_COMPRESS_QLTY, baos);
                pdfByteArray = baos.toByteArray();
                AppUtils.getInstance().showLog("byteArray" + pdfByteArray, AadharAccountActivity.class);

            } catch (Exception e) {
                Toast.makeText(this, getString(R.string.toast_file_upload_error), Toast.LENGTH_LONG).show();
            }

        } else {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {
                    Toast.makeText(AadharAccountActivity.this, getString(R.string.canceld), Toast.LENGTH_LONG).show();
                    AppUtils.getInstance().showLog("cancled", AadharAccountActivity.class);
                    aadharDetailLinearLayout.setVisibility(View.VISIBLE);

                    enterAadhaeNameET.setEnabled(true);
                    enterAadhaeNameET.setFocusable(true);

                    aadharGenderEt.setEnabled(true);
                    aadharGenderEt.setFocusable(true);

                    aadharFatherNameEt.setEnabled(true);
                    aadharFatherNameEt.setFocusable(true);


                    aadharAddressEt.setEnabled(true);
                    aadharAddressEt.setFocusable(true);


                    aadharMobileNumberEt.setEnabled(true);
                    aadharMobileNumberEt.setFocusable(true);

                    aadharYobEt.setEnabled(true);
                    aadharYobEt.setFocusable(true);

                    aadharYobEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (hasFocus) {
                                long star, end;

                                int mYear, mMonth, mDay;
                                int mYear1, mMonth1, mDay1;
                                if (v == aadharYobEt) {
                                    final Calendar c = Calendar.getInstance();
                                    mYear = c.get(Calendar.YEAR);
                                    mMonth = c.get(Calendar.MONTH);
                                    mDay = c.get(Calendar.DAY_OF_MONTH);
                                    //c.add(Calendar.YEAR, -18);


                                    DatePickerDialog datePickerDialog = new DatePickerDialog(AadharAccountActivity.this,
                                            new DatePickerDialog.OnDateSetListener() {
                                                @SuppressLint("SetTextI18n")
                                                @Override
                                                public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                                                    String day = "";
                                                    if (dayOfMonth < 10)
                                                        day = "0" + dayOfMonth;
                                                    else
                                                        day = String.valueOf(dayOfMonth);
                                                    AppUtils.getInstance().showLog("daywithformate" + day, AddShgMemberActivity.class);

                                                    String monthc = "";
                                                    month = month + 1;
                                                    if (month <= 9)
                                                        monthc = "0" + month;
                                                    else
                                                        monthc = String.valueOf(month);
                                                    AppUtils.getInstance().showLog("monthwithformate" + monthc, AddShgMemberActivity.class);

                                                    aadharYobEt.setText(day + "-" + monthc + "-" + year);
                                                    yob = aadharYobEt.getText().toString().trim();
                                                }
                                            }, mYear - 18, mMonth, mDay);
                                    c.set(mYear - 18, mMonth, mDay);
                                    long value = c.getTimeInMillis();
                                    datePickerDialog.getDatePicker().setMaxDate(value);
                                    /*mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());*/
                                    datePickerDialog.show();
                                }
                            }
                        }

                    });

                    aadharYobEt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int mYear, mMonth, mDay;
                            if (v == aadharYobEt) {
                                final Calendar c = Calendar.getInstance();
                                mYear = c.get(Calendar.YEAR);
                                mMonth = c.get(Calendar.MONTH);
                                mDay = c.get(Calendar.DAY_OF_MONTH);


                                DatePickerDialog datePickerDialog = new DatePickerDialog(AadharAccountActivity.this,
                                        new DatePickerDialog.OnDateSetListener() {
                                            @Override
                                            public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                                                String day = "";
                                                if (dayOfMonth < 10)
                                                    day = "0" + dayOfMonth;
                                                else
                                                    day = String.valueOf(dayOfMonth);
                                                AppUtils.getInstance().showLog("daywithformate" + day, AddShgMemberActivity.class);

                                                String monthc = "";
                                                if (month < 10)
                                                    monthc = "0" + (month + 1);
                                                else
                                                    monthc = String.valueOf(month);
                                                AppUtils.getInstance().showLog("monthwithformate" + monthc, AddShgMemberActivity.class);

                                                aadharYobEt.setText(day + "-" + monthc + "-" + year);
                                                yob = aadharYobEt.getText().toString().trim();
                                            }
                                        }, mYear, mMonth, mDay);
                                datePickerDialog.show();
                            }
                        }
                    });

                    cardScanStatus = "0";
                    scanStatus = "N";

                } else {
                    // Log.d(TAG, "onActivityResult: " + result.toString());
                    XmlPullParserFactory pullParserFactory;
                    try {
                        pullParserFactory = XmlPullParserFactory.newInstance();
                        parser = pullParserFactory.newPullParser();
                        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                        parser.setInput(new StringReader(result.getContents()));
                        processParsing(parser);
                    } catch (Exception e) {
                        // Log.d(TAG, "EXCEPTION " + e);
                        Dialog dialog = new Dialog(AadharAccountActivity.this);
                        dialog.setContentView(R.layout.my_qr_errorcode_dialog);
                        Button ok = (Button) dialog.findViewById(R.id.buttonOk);
                        dialog.setCancelable(false);
                        dialog.show();
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        // Toast.makeText(AadharAccountActivity.this, "excpe" + e, Toast.LENGTH_LONG).show();
                        AppUtils.getInstance().showLog("EXCEPTION" + e, AadharAccountActivity.class);
                    }
                    AppUtils.getInstance().showLog("response", AadharAccountActivity.class);
                }
            } else {
                //super.onActivityResult(requestCode, resultCode, data);
                Toast.makeText(AadharAccountActivity.this, getString(R.string.not_scaned), Toast.LENGTH_LONG).show();
            }
        }

    }

    private void processParsing(@NonNull XmlPullParser parser) throws IOException, XmlPullParserException {
        int eventType = parser.getEventType();
        AppUtils.getInstance().showLog("eventType" + eventType, AadharAccountActivity.class);

        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_DOCUMENT) {
                AppUtils.getInstance().showLog("startDocuments", AadharAccountActivity.class);
            } else if (eventType == XmlPullParser.START_TAG) {
                AppUtils.getInstance().showLog("Start tag " + parser.getName(), AadharAccountActivity.class);
                if (parser.getName().equalsIgnoreCase("PrintLetterBarcodeData")) {
                    uid = parser.getAttributeValue(null, "uid");
                    aadharUid = enterAadharEt.getText().toString();
                    if (aadharUid == null || aadharUid.equalsIgnoreCase("")) {
                        enterAadharEt.setText(uid);
                    } else {
                        if (aadharUid.equalsIgnoreCase(uid)) {
                            aadharUid = uid;
                            progressDialog.show();
                            int att_count = parser.getAttributeCount();
                            //Log.d(TAG, "onActivityResult: " + att_count);
                            //  String att_name =parser.getAttributeName()
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
                                    aadharDetailLinearLayout.setVisibility(View.VISIBLE);
                                    enterAadhaeNameET.setText(name);
                                    aadharGenderEt.setText(gender);
                                    aadharFatherNameEt.setText(co);
                                    aadharYobEt.setText(yob);
                                    aadharAddressEt.setText(house + ", " + loc + ", " + vtc + ", " + dist + ", " + state + ", " + pc);

                                    enterAadhaeNameET.setEnabled(false);
                                    enterAadhaeNameET.setFocusable(false);

                                    aadharGenderEt.setEnabled(false);
                                    aadharGenderEt.setFocusable(false);

                                    aadharFatherNameEt.setEnabled(false);
                                    aadharFatherNameEt.setFocusable(false);

                                    aadharYobEt.setEnabled(false);
                                    aadharYobEt.setFocusable(false);

                                    aadharAddressEt.setEnabled(false);
                                    aadharAddressEt.setFocusable(false);
                                    scanStatus = "Y";

                                }
                            }, 3000); // 3000

                        } else {
                            //show a custom dialog for enter wrong pasword
                            //Toast.makeText(getApplicationContext(), getString(R.string.enterErongAadhar), Toast.LENGTH_SHORT).show();
                            DialogFactory.getInstance().showAlertDialog(AadharAccountActivity.this, 0, getString(R.string.info), getString(R.string.enterErongAadhar),
                                    getString(R.string.ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(@NonNull DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }, false);
                        }
                    }
                    AppUtils.getInstance().showLog("Start tag UID " + uid, AadharAccountActivity.class);
                    //  Toast.makeText(getApplicationContext(),"uid"+uid,Toast.LENGTH_SHORT).show();
                }

            } else if (eventType == XmlPullParser.END_TAG) {
                AppUtils.getInstance().showLog("End tag " + parser.getName(), AadharAccountActivity.class);
            } else if (eventType == XmlPullParser.TEXT) {
                AppUtils.getInstance().showLog("Text" + parser.getText(), AadharAccountActivity.class);
            }
            eventType = parser.next();
        }
        AppUtils.getInstance().showLog("EndDocuments", AadharAccountActivity.class);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getPrefKeyShgMemberStatus()
                , "goToMemberList", AadharAccountActivity.this);
        Intent intent = new Intent(AadharAccountActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }

    public byte[] convertPDFToByteArray(File file, String path) {

        InputStream inputStream = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {

            inputStream = new FileInputStream(path);

            byte[] buffer = new byte[(int) file.length()];
            baos = new ByteArrayOutputStream();

            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return baos.toByteArray();
    }

    @Override
    public void onLoadComplete(@NonNull Loader loader, @Nullable Object data) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}

/*
</?xml version="1.0" encoding="UTF-8"?>
 <PrintLetterBarcodeData
  uid="563455249021"
  name="Gajender Singh"
  gender="MALE"
  yob="1989"
  co="S/O: Babu Singh"
  lm="null"
  loc="devendra puri"
  vtc="Modinagar"
  po="Modi Nagar"
  dist="Ghaziabad"
  state="Uttar Pradesh"
  pc="201204"
  dob="26-07-1989"/>*/
/*
<?xml version="1.0" encoding="UTF-8"?>
<PrintLetterBarcodeData
 uid="402561545426"
 name="Happy Bhalla"
 gender="M"
 yob="1992"
 co="S/O Mahender Pal"
 house="# 284/23"
 loc="DLF COLONY"
 vtc="Rohtak"
 po="Rohtak"
 dist="Rohtak"
 state="Haryana"
 pc="124001"/>

*/

 /*INTENT_RESULTFormat: QR_CODE
         Contents: <?xml version="1.0" encoding="UTF-8"?>
<PrintLetterBarcodeData
 uid="994146194834"
 name="Shashi"
 gender="F"
 yob="1964"
 co="W/O: Mohinder Pal"
 house="284/23"
 loc="D.L.F.colony"
 vtc="Rohtak"
 po="Rohtak"
 dist="Rohtak"
 subdist="Rohtak"
 state="Haryana"
 pc="124001"
 dob="12/04/1964"/>
 Raw bytes: (290 bytes)
 Orientation: null
 EC level: M
 Barcode image: /data/user/0/com.example.aadharscanner/cache/barcodeimage6964163603989198709.jpg
 Original intent: Intent { act=com.google.zxing.client.android.SCAN flg=0x80000 (has extras) }
*/

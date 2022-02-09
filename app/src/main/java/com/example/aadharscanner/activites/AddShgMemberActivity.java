package com.example.aadharscanner.activites;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.aadharscanner.R;
import com.example.aadharscanner.adapters.ShgMemberListAdapter;
import com.example.aadharscanner.database.AddMemberAadharDetail;
import com.example.aadharscanner.database.BankBranchDetailData;
import com.example.aadharscanner.database.BankBranchDetailDataDao;
import com.example.aadharscanner.database.BankDetailData;
import com.example.aadharscanner.database.BankDetailDataDao;
import com.example.aadharscanner.database.GpsDataDao;
import com.example.aadharscanner.database.ShgDetailDataDao;
import com.example.aadharscanner.database.ShgMemberDetailsData;
import com.example.aadharscanner.database.ShgMemberDetailsDataDao;
import com.example.aadharscanner.database.ShgMemberRegistrationSyncData;
import com.example.aadharscanner.database.ShgMemberRegistrationSyncDataDao;
import com.example.aadharscanner.database.UpdatedDesignation;
import com.example.aadharscanner.database.UpdatedDesignationDao;
import com.example.aadharscanner.database.VillageDataDao;
import com.example.aadharscanner.utils.AppConstant;
import com.example.aadharscanner.utils.AppUtils;
import com.example.aadharscanner.utils.Cryptography;
import com.example.aadharscanner.utils.DatePicker;
import com.example.aadharscanner.utils.DialogFactory;
import com.example.aadharscanner.utils.FileManager;
import com.example.aadharscanner.utils.FileUtility;
import com.example.aadharscanner.utils.PreferenceFactory;
import com.example.aadharscanner.utils.PreferenceManager;
import com.example.aadharscanner.utils.SingletonVolley;
import com.example.aadharscanner.utils.SyncData;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.aadharscanner.utils.AppConstant.IMAGE_COMPRESS_QLTY;

public class AddShgMemberActivity extends AppCompatActivity {
    @Nullable
    @BindView(R.id.aadharScannerIV1)
    ImageView aadharScannerIV;

    @Nullable
    @BindView(R.id.aadharImageView)
    ImageView aadharImageView;

    @Nullable
    @BindView(R.id.passbookImageView)
    ImageView passbookImageView;

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
    @BindView(R.id.accountNumberEt)
    TextInputEditText accountNumberEt;

    @Nullable
    @BindView(R.id.confirmAccountNumberEt)
    TextInputEditText confirmAccountNumberEt;

    @Nullable
    @BindView(R.id.aadharMobileNumberEt)
    EditText aadharMobileNumberEt;

    @Nullable
    @BindView(R.id.socialCatagorySpinner)
    MaterialBetterSpinner socialCatagorySpinner;

    @Nullable
    @BindView(R.id.pvgtStatusSpinner)
    MaterialBetterSpinner pvgtStatusSpinner;


    @Nullable
    @BindView(R.id.disablitySpinner)
    MaterialBetterSpinner disablitySpinner;

    @Nullable
    @BindView(R.id.religionSpinner)
    MaterialBetterSpinner religionSpinner;

    @Nullable
    @BindView(R.id.shoopkeeperMBS)
    MaterialBetterSpinner shoopkeeperMBS;


    @Nullable
    @BindView(R.id.seccMSB)
    MaterialBetterSpinner seccMSB;

    @Nullable
    @BindView(R.id.pipSpinner)
    MaterialBetterSpinner pipSpinner;

    @Nullable
    @BindView(R.id.leaderSpinner)
    MaterialBetterSpinner leaderSpinner;

    @Nullable
    @BindView(R.id.educationSpinner)
    MaterialBetterSpinner educationSpinner;

    @Nullable
    @BindView(R.id.pmjjySpinner)
    MaterialBetterSpinner pmjjySpinner;

    @Nullable
    @BindView(R.id.pmsbySpinner)
    MaterialBetterSpinner pmsbySpinner;

    @Nullable
    @BindView(R.id.lifeInsuranceSpinner)
    MaterialBetterSpinner lifeInsuranceSpinner;

    @Nullable
    @BindView(R.id.pensionSpinner)
    MaterialBetterSpinner pensionSpinner;

    @Nullable
    @BindView(R.id.spinner_BankNameSpin)
    MaterialBetterSpinner spinner_BankNameSpin;

    @Nullable
    @BindView(R.id.spinner_BankBranchSpin)
    MaterialBetterSpinner spinner_BankBranchSpin;

    @Nullable
    @BindView(R.id.aadharSeededSpinner)
    MaterialBetterSpinner aadharSeededSpinner;


    @Nullable
    @BindView(R.id.dateOfJoiningTv)
    TextView dateOfJoiningTv;

    @Nullable
    @BindView(R.id.member_ShgCodeTv)
    TextView member_ShgCodeTv;

    @Nullable
    @BindView(R.id.member_ShgNameTv)
    TextView member_ShgNameTv;

    @Nullable
    @BindView(R.id.member_VillageNameTv)
    TextView member_VillageNameTv;

    @Nullable
    @BindView(R.id.member_GpNameTv)
    TextView member_GpNameTv;

    @Nullable
    @BindView(R.id.otherEduET)
    TextView otherEduET;

    @Nullable
    @BindView(R.id.otherEduTIL)
    TextInputLayout otherEduTIL;


    @Nullable
    @BindView(R.id.ifcsTv)
    EditText ifcsTv;

    @Nullable
    @BindView(R.id.detailLinearLayout)
    LinearLayout aadharDetailLinearLayout;

    @Nullable
    @BindView(R.id.accDetailLinearLayout)
    LinearLayout accountDetailLinearLayout;

    @Nullable
    @BindView(R.id.aadharPhotoLinearLayout)
    LinearLayout aadharPhotoLinearLayout;

    @Nullable
    @BindView(R.id.photoLinearLayout)
    LinearLayout photoLinearLayout;

    @Nullable
    @BindView(R.id.accNumberTIL)
    TextInputLayout accNumberTIL;

    @Nullable
    @BindView(R.id.saveDataBtn)
    TextView saveDataBtn;

    @Nullable
    @BindView(R.id.takAadhaarSelfiBtn)
    Button takAadhaarSelfiBtn;

    @Nullable
    @BindView(R.id.takeAgainSelfiBtn)
    Button takeAgainSelfiBtn;

    @Nullable
    @BindView(R.id.takePassbookPhotoBtn)
    Button takePassbookPhotoBtn;

    @Nullable
    @BindView(R.id.takeAgainPassbookBtn)
    Button takeAgainPassbookBtn;

    @Nullable
    @BindView(R.id.backBtn)
    TextView backBtn;


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
    ProgressDialog progressDialog;

    private byte[] imageByteArray;
    private byte[] imageSelfiByteArray;

    //arraylist for all
    private List<BankDetailData> bankNameDataListItems;
    private List<BankBranchDetailData> bankBranchesDataListItems;


    //string array for set on adapter
    private String[] socialCatagoryArray;
    private String[] disabilityArray;
    private String[] religionArray;
    private String[] pipCatagoryArray;
    private String[] leaderArray;
    private String[] educationStandardArray;
    private String[] bankNameArray;
    private String[] bankBranchArray;
    private String[] pmjjyArray;
    private String[] pmsbyArray;
    private String[] lifeInsuranceArray;
    private String[] pensionArray;
    private String[] aadharSeededArray;
    private String[] shoopkeeperArray;
    private String[] seccArray;


    //array adapter set on spinner
    ArrayAdapter<String> socialCatagoryAdapter;
    ArrayAdapter<String> diasablityAdapter;
    ArrayAdapter<String> religionAdapter;
    ArrayAdapter<String> pipAdapter;
    ArrayAdapter<String> leaderAdapter;
    ArrayAdapter<String> educationAdapter;
    ArrayAdapter<String> bankNameArrayAdapter;
    ArrayAdapter<String> bankBranchNameArrayAdapter;
    ArrayAdapter<String> pmjjyArrayAdapter;
    ArrayAdapter<String> pmsbyArrayAdapter;
    ArrayAdapter<String> lifeInsuranceArrayAdapter;
    ArrayAdapter<String> pensionArrayAdapter;
    ArrayAdapter<String> aadharSeededArrayAdapter;
    ArrayAdapter<String> shoopkeeperAdapter;
    ArrayAdapter<String> seccAdapter;
    private ArrayAdapter<String> pvgtStatusAdapter;


    // string for all input
    @NonNull
    private String socialCatgory = "";
    @NonNull
    private String disablity = "";
    @NonNull
    private String religon = "";
    @NonNull
    private String pip = "";
    @NonNull
    private String leader = "";
    @NonNull
    private String education = "";
    @NonNull
    private String bankName = "";
    private String bankCode = "";
    private String bankBranchCode = "";
    @NonNull
    private String bankBranchName = "";
    private String ifscCode = "";
    private String bankAccLength = "";
    @NonNull
    private String pmjjy = "";
    @NonNull
    private String pmsby = "";
    @NonNull
    private String lifeInsurance = "";
    @NonNull
    private String pension = "";
    @NonNull
    private String aadharSeeded = "";
    @NonNull
    private String accountNumber = "";
    @NonNull
    private String confirmAccNumber = "";
    @NonNull
    private String isShopKeeper = "";
    @NonNull
    private String seccNo = "";
    @NonNull
    private String otherEducation = "";
    int accountLength;

    //aadhar card details
    private String uid = "";
    private String name = "";
    private String gender = "";
    private String co = "";
    private String yob = "";
    private String house = "";
    private String loc = "";
    private String vtc = "";
    private String dist = "";
    private String state = "";
    private String pc = "";
    @NonNull
    private String lm = "";
    @NonNull
    private String po = "";
    @NonNull
    private String completAddress = "";
    private String dob = "";
    private String aadharUid = "";
    @Nullable
    private String shgCode;
    @Nullable
    private String blockCode;
    @Nullable
    private String gpCode;
    @Nullable
    private String villageCode;
    @Nullable
    private String memberUniqueCode;
    @NonNull
    private String mobileNumber = "";
    @NonNull
    private String doj = "";
    @NonNull
    private String cardScanStatus = "0", scanStatus;
    @NonNull
    private String pvgtStatus = "";


    private String pdfFileName;
    public static final int FILE_PICKER_REQUEST_CODE = 1;
    private byte[] pdfByteArray;
    private int fileSize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shg_member);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(AddShgMemberActivity.this);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);
        disablitySpinner.setFocusableInTouchMode(false);
        religionSpinner.setFocusableInTouchMode(false);
        pipSpinner.setFocusableInTouchMode(false);
        leaderSpinner.setFocusableInTouchMode(false);
        educationSpinner.setFocusableInTouchMode(false);
        pmjjySpinner.setFocusableInTouchMode(false);
        pmsbySpinner.setFocusableInTouchMode(false);
        lifeInsuranceSpinner.setFocusableInTouchMode(false);
        pensionSpinner.setFocusableInTouchMode(false);
        spinner_BankBranchSpin.setFocusableInTouchMode(false);
        spinner_BankNameSpin.setFocusableInTouchMode(false);
        aadharSeededSpinner.setFocusableInTouchMode(false);
        confirmAccountNumberEt.setEnabled(false);


        shgCode = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getPrefKeyShgCode()
                , AddShgMemberActivity.this);
        blockCode = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getPrefKeyLoginBlockCode()
                , AddShgMemberActivity.this);
        gpCode = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getPrefKeyGpCode()
                , AddShgMemberActivity.this);

        villageCode = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getPrefKeyVillageCode()
                , AddShgMemberActivity.this);
        member_VillageNameTv.setText(getVillageName(villageCode));
        member_ShgCodeTv.setText(shgCode);
        member_ShgNameTv.setText(getShgName(shgCode, villageCode));
        member_GpNameTv.setText(getGpName(gpCode));

        initilazion();
        // seccBindData();
        // seccListner();
        isShopKeeperBindData();
        isShopKeeperListner();
        bindData();
        setListner();
        bindBankData();
        setBankListner();
        bindinsuranceData();
        setInsuranceListner();

        upload_concentLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //launchPicker();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,
                        FILE_PICKER_REQUEST_CODE);
            }
        });
        enterAadharEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(@NonNull Editable s) {

                    if (s.toString().length() == 12) {
                        /**check enter aadhar card exist in our local database or not**/
                        if (aadharExistInDb(s.toString())) {
                            aadharScannerIV.setVisibility(View.VISIBLE);
                        } else {
                            enterAadharEt.setText("");
                            Toast.makeText(AddShgMemberActivity.this, getString(R.string.aadhar_exists), Toast.LENGTH_LONG).show();
                        }
                        //aadharScannerIV.setVisibility(View.VISIBLE);

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

        aadharMobileNumberEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (aadharMobileNumberEt.getText().toString().length() < 10) {
                        aadharMobileNumberEt.setError(getString(R.string.error_mobile));

                    }
                } else {
                    mobileNumber = aadharMobileNumberEt.getText().toString();
                }
            }
        });
    }

    @OnClick(R.id.backBtn)
    public void onBackClick() {
        PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getPrefKeyShgMemberStatus(), "goToMemberList"
                , AddShgMemberActivity.this);
        AppUtils.getInstance().makeIntent(AddShgMemberActivity.this, HomeActivity.class, true);
    }

    public boolean aadharExistInDb(String uid) {
        boolean isValid = true;
        List<AddMemberAadharDetail> uidList = SplashScreen.getInstance().getDaoSession().getAddMemberAadharDetailDao().queryBuilder().build().list();
        if (uidList.isEmpty()) {
            isValid = true;
        } else {
            for (AddMemberAadharDetail adUid : uidList) {
                if (adUid.getUidNumber().equalsIgnoreCase(uid)) {
                    isValid = false;
                } else {
                    isValid = true;
                }
            }
        }
        return isValid;
    }

    private String getGpName(String gpCode) {
        String gpName = "";
        gpName = SplashScreen.getInstance().getDaoSession().getGpsDataDao().queryBuilder()
                .where(GpsDataDao.Properties.GpCode.eq(gpCode)).unique().getGpName();
        return gpName;
    }

    private String getVillageName(String villageCode) {
        String villageName = "";
        villageName = SplashScreen.getInstance().getDaoSession().getVillageDataDao().queryBuilder()
                .where(VillageDataDao.Properties.VillageCode.eq(villageCode)).unique().getVillageName();
        return villageName;
    }

    private String getShgName(String shgCode, String villageCode) {
        String name = "";
        //write a detatch all line for remove duplicate
        name = SplashScreen.getInstance().getDaoSession().getShgDetailDataDao().queryBuilder()
                .where(ShgDetailDataDao.Properties.ShgCode.eq(shgCode)
                        , ShgDetailDataDao.Properties.VillageCode.eq(villageCode)).unique().getShgGroupName();
        return name;
    }


    private void initilazion() {
        bankNameDataListItems = new ArrayList<>();
        bankBranchesDataListItems = new ArrayList<>();

    }

    private void bindData() {
        socialCatagoryArray = getResources().getStringArray(R.array.socialCatagory);
        socialCatagoryAdapter = new ArrayAdapter<String>(AddShgMemberActivity.this,
                R.layout.spinner_textview, socialCatagoryArray);
        socialCatagorySpinner.setAdapter(socialCatagoryAdapter);
    }

    private void setListner() {
        socialCatagorySpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                socialCatgory = socialCatagorySpinner.getText().toString();

                disabilityArray = getResources().getStringArray(R.array.disablity);
                diasablityAdapter = new ArrayAdapter<String>(AddShgMemberActivity.this,
                        R.layout.spinner_textview, disabilityArray);
                disablitySpinner.setAdapter(diasablityAdapter);

                if (socialCatgory.equalsIgnoreCase("ST") && new AadharAccountActivity().getLoginInfo()
                        .get(0).getPvgtStatus().equalsIgnoreCase("Y")) {
                    pvgtStatusSpinner.setVisibility(View.VISIBLE);
                    pvgtStatusAdapter = new ArrayAdapter<String>(AddShgMemberActivity.this, R.layout.spinner_textview
                            , getResources().getStringArray(R.array.commanArray));
                    pvgtStatusSpinner.setAdapter(pvgtStatusAdapter);
                } else {
                    pvgtStatusSpinner.setVisibility(View.GONE);
                    pvgtStatus = "N";
                }
            }
        });

        pvgtStatusSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pvgtStatus = pvgtStatusSpinner.getText().toString().trim();
            }
        });
        disablitySpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                disablity = disablitySpinner.getText().toString();
                religionArray = getResources().getStringArray(R.array.religion);
                religionAdapter = new ArrayAdapter<String>(AddShgMemberActivity.this,
                        R.layout.spinner_textview, religionArray);
                religionSpinner.setAdapter(religionAdapter);
            }
        });
        religionSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                religon = religionSpinner.getText().toString();
                pipCatagoryArray = getResources().getStringArray(R.array.pipCatagory);
                pipAdapter = new ArrayAdapter<String>(AddShgMemberActivity.this,
                        R.layout.spinner_textview, pipCatagoryArray);
                pipSpinner.setAdapter(pipAdapter);

            }
        });
        pipSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pip = pipSpinner.getText().toString();
                leaderArray = getResources().getStringArray(R.array.leaderArray);
                leaderAdapter = new ArrayAdapter<String>(AddShgMemberActivity.this,
                        R.layout.spinner_textview, leaderArray);
                leaderSpinner.setAdapter(leaderAdapter);

            }
        });
        leaderSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                leader = leaderSpinner.getText().toString();
                if (leader.equalsIgnoreCase("Member")){
                    leader = leaderSpinner.getText().toString();
                    educationStandardArray = getResources().getStringArray(R.array.educationStandard);
                    educationAdapter = new ArrayAdapter<String>(AddShgMemberActivity.this,
                            R.layout.spinner_textview, educationStandardArray);
                    educationSpinner.setAdapter(educationAdapter);
                }

                else {
                 /*   QueryBuilder queryBuilder =SplashScreen.getInstance().getDaoSession().getShgMemberDetailsDataDao().queryBuilder();
                    List<ShgMemberDetailsData> shgMemberDetailsDataList=queryBuilder.where(ShgMemberDetailsDataDao.Properties.ShgCode.eq(shgCode)).build().list();
                    for (ShgMemberDetailsData shgMemberDetailsData:shgMemberDetailsDataList){
                        if (shgMemberDetailsData.getLeadership().equalsIgnoreCase(leader)){
                            leaderSpinner.setError(leader+" "+getResources().getString(R.string.error_leadership));
                            return;
                        }
                    }*/


                    /*build a logic here  for adding member
                    * if adding member is first than it will be president forcefully
                    * if adding member is second and first added member is president  than it will be secratory  forcefully
                    * if adding member is 3rd and president and secratory both are available in the group than it will be treaserur forcefully  */

                    QueryBuilder<UpdatedDesignation> queryBuilder=SplashScreen.getInstance().getDaoSession().getUpdatedDesignationDao().queryBuilder();

                    int updatedDesignationCount = queryBuilder.where(UpdatedDesignationDao.Properties.Designation.eq(leader)
                            ,queryBuilder.and(UpdatedDesignationDao.Properties.ShgCode.eq(shgCode),UpdatedDesignationDao.Properties.Designation.eq(leader))).build().list().size();
                    AppUtils.getInstance().showLog("updatedDesignationCount"+updatedDesignationCount, ShgMemberListAdapter.class);

                    if (updatedDesignationCount>0){
                        leader="";
                       // showSnacks(view,leader+" "+getResources().getString(R.string.error_designation));
                        Toast.makeText(AddShgMemberActivity.this,leader+" "+getResources().getString(R.string.error_designation),Toast.LENGTH_LONG).show();
                        return;
                    }

                    educationStandardArray = getResources().getStringArray(R.array.educationStandard);
                    educationAdapter = new ArrayAdapter<String>(AddShgMemberActivity.this,
                            R.layout.spinner_textview, educationStandardArray);
                    educationSpinner.setAdapter(educationAdapter);

                }


            }
        });
        educationSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                education = educationSpinner.getText().toString();
                /*<item>GRADUATE</item>
        <item>POST GRADUATE</item>
        <item>OTHER</item>*/
                otherEduTIL.setVisibility(View.GONE);
                if (education.equalsIgnoreCase("OTHER"))
                    otherEduTIL.setVisibility(View.VISIBLE);
                else if (education.equalsIgnoreCase("POST GRADUATE"))
                    education = "PG";
                else if (education.equalsIgnoreCase("GRADUATE"))
                    education = "G";
            }
        });
    }


    private void bindBankData() {
        bankNameDataListItems = SplashScreen.getInstance().getDaoSession().getBankDetailDataDao()
                .queryBuilder().build().list();

        bankNameArray = new String[bankNameDataListItems.size()];
        for (int i = 0; i < bankNameDataListItems.size(); i++) {
            bankNameArray[i] = bankNameDataListItems.get(i).getBankName();
        }
        bankNameArrayAdapter = new ArrayAdapter<String>(AddShgMemberActivity.this,
                R.layout.spinner_textview, bankNameArray);
        spinner_BankNameSpin.setAdapter(bankNameArrayAdapter);
    }

    private void setBankListner() {
        spinner_BankNameSpin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clearFocus(1);
                bankName = spinner_BankNameSpin.getText().toString();
                bankCode = getBankCode(bankName);
                bankAccLength = getBankAccLength(bankCode);
                bankBranchesDataListItems = SplashScreen.getInstance().getDaoSession().getBankBranchDetailDataDao()
                        .queryBuilder().where(BankBranchDetailDataDao.Properties.BankCode.eq(bankCode)).build().list();
                bankBranchArray = new String[bankBranchesDataListItems.size()];
                for (int i = 0; i < bankBranchesDataListItems.size(); i++) {
                    bankBranchArray[i] = bankBranchesDataListItems.get(i).getBankBranchName();
                }
                bankBranchNameArrayAdapter = new ArrayAdapter<String>(AddShgMemberActivity.this,
                        R.layout.spinner_textview, bankBranchArray);
                spinner_BankBranchSpin.setAdapter(bankBranchNameArrayAdapter);
            }
        });
        spinner_BankBranchSpin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clearFocus(2);
                bankBranchName = spinner_BankBranchSpin.getText().toString();
                bankBranchCode = getBankBranchCode(bankBranchName);
                accountDetailLinearLayout.setVisibility(View.VISIBLE);
                ifscCode = getIfscCode(bankBranchCode);

                //set animation fot open a layout
                accountDetailLinearLayout.setVisibility(View.VISIBLE);
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.slide_down);
                accountDetailLinearLayout.startAnimation(animation);

                ifcsTv.setText(ifscCode);
                ifcsTv.setEnabled(false);

                /****write logic if length exist
                 * and user don't enter a
                 * complet length ac number show
                 a msg to user for entring a complet acc number ******/

                if (bankAccLength.equalsIgnoreCase("Bank A/c length N/A !!!")) {
                    accNumberTIL.setHelperText(getString(R.string.enterValidAccMsg));
                } else {
                    accountLength = Integer.parseInt(bankAccLength);
                    setAcclength();
                    accNumberTIL.setHelperText(getString(R.string.enter) + bankAccLength + getString(R.string.enterDigitMsg));
                    accNumberTIL.setCounterEnabled(true);
                    accNumberTIL.setCounterMaxLength(accountLength);
                }

                aadharSeededArray = getResources().getStringArray(R.array.commanArray);
                aadharSeededArrayAdapter = new ArrayAdapter<String>(AddShgMemberActivity.this,
                        R.layout.spinner_textview, aadharSeededArray);
                aadharSeededSpinner.setAdapter(aadharSeededArrayAdapter);

                accountNumberEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            accountNumberEt.setInputType(InputType.TYPE_CLASS_NUMBER |
                                    InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            accountNumber = accountNumberEt.getText().toString();


                        } else {
                            confirmAccountNumberEt.setEnabled(true);
                            accountNumberEt.setInputType(InputType.TYPE_CLASS_NUMBER);
                            accountNumberEt.setSelection(accountNumberEt.getText().toString().length());
                            accountNumber = accountNumberEt.getText().toString();
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
                        if (accountNumberEt.getText().toString().trim().length() >= 6) {
                            if (confirmAccountNumberEt.getText().toString() != null) {
                                if (accountNumberEt.getText().toString().trim().length() == confirmAccountNumberEt.getText().toString().trim().length()) {
                                    if (!confirmAccountNumberEt.getText().toString().equalsIgnoreCase(accountNumberEt.getText().toString())) {
                                        confirmAccountNumberEt.setError(getString(R.string.accountNoIsWrong));
                                        //  submitAccountBtn.setEnabled(false);
                                        // submitAccountBtn.setBackgroundColor(getResources().getColor(R.color.colorButtonLightBlue));

                                    } else {
                                        confirmAccountNumberEt.setError(null);
                                        //  submitAccountBtn.setEnabled(true);
                                        //  submitAccountBtn.setBackgroundColor(getResources().getColor(R.color.colorButtonBlue));
                                    }
                                }
                            }
                        } else {
                            accountNumberEt.setError(getString(R.string.error_acc_length));
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

                        if (!confirmAccountNumberEt.getText().toString().equalsIgnoreCase(accountNumberEt.getText().toString())) {
                            confirmAccountNumberEt.setError(getString(R.string.accountNoIsWrong));
                            //submitAccountBtn.setEnabled(false);
                            // submitAccountBtn.setBackgroundColor(getResources().getColor(R.color.colorButtonLightBlue));

                        } else {
                            confirmAccountNumberEt.setError(null);
                            // submitAccountBtn.setEnabled(true);
                            // submitAccountBtn.setBackgroundColor(getResources().getColor(R.color.colorButtonBlue));
                        }
                    }
                });


            }
        });
        aadharSeededSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                aadharSeeded = aadharSeededSpinner.getText().toString();
                takePassbookPhotoBtn.setVisibility(View.VISIBLE);
                /*----write a condition for
                check acc and confirm acc is
                valid or not or in a propr length----*/

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

    private void isShopKeeperBindData() {
        shoopkeeperArray = getResources().getStringArray(R.array.commanArray);
        shoopkeeperAdapter = new ArrayAdapter<String>(AddShgMemberActivity.this
                , R.layout.spinner_textview, shoopkeeperArray);
        shoopkeeperMBS.setAdapter(shoopkeeperAdapter);
    }

    private void isShopKeeperListner() {
        shoopkeeperMBS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                isShopKeeper = shoopkeeperMBS.getText().toString().trim();
            }
        });
    }

    private void seccBindData() {
        seccArray = getResources().getStringArray(R.array.commanArray);
        seccAdapter = new ArrayAdapter<String>(AddShgMemberActivity.this
                , R.layout.spinner_textview, seccArray);
        seccMSB.setAdapter(seccAdapter);
    }

    private void seccListner() {
        seccMSB.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                seccNo = seccMSB.getText().toString().trim();
            }
        });
    }


    private void bindinsuranceData() {
        pmjjyArray = getResources().getStringArray(R.array.commanArray);
        pmjjyArrayAdapter = new ArrayAdapter<String>(AddShgMemberActivity.this,
                R.layout.spinner_textview, pmjjyArray);
        pmjjySpinner.setAdapter(pmjjyArrayAdapter);
    }

    private void setInsuranceListner() {
        pmjjySpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pmjjy = pmjjySpinner.getText().toString();
                pmsbyArray = getResources().getStringArray(R.array.commanArray);
                pmsbyArrayAdapter = new ArrayAdapter<String>(AddShgMemberActivity.this,
                        R.layout.spinner_textview, pmsbyArray);
                pmsbySpinner.setAdapter(pmsbyArrayAdapter);

            }
        });
        pmsbySpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pmsby = pmsbySpinner.getText().toString();
                lifeInsuranceArray = getResources().getStringArray(R.array.commanArray);
                lifeInsuranceArrayAdapter = new ArrayAdapter<String>(AddShgMemberActivity.this,
                        R.layout.spinner_textview, lifeInsuranceArray);
                lifeInsuranceSpinner.setAdapter(lifeInsuranceArrayAdapter);

            }
        });
        lifeInsuranceSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lifeInsurance = lifeInsuranceSpinner.getText().toString();
                pensionArray = getResources().getStringArray(R.array.commanArray);
                pensionArrayAdapter = new ArrayAdapter<String>(AddShgMemberActivity.this,
                        R.layout.spinner_textview, pensionArray);
                pensionSpinner.setAdapter(pensionArrayAdapter);

            }
        });
        pensionSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pension = pensionSpinner.getText().toString();
            }
        });
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

    private String getBankBranchCode(@NonNull String bankBranchName) {
        String bankBranchCode = "";
        for (int i = 0; i < bankBranchesDataListItems.size(); i++) {
            if (bankBranchName.equalsIgnoreCase(bankBranchesDataListItems.get(i).getBankBranchName())) {
                bankBranchCode = bankBranchesDataListItems.get(i).getBankBranchCode();
            }
        }
        return bankBranchCode;
    }

    private String getIfscCode(String bankBranchCode) {
        String ifsc = "";
        ifsc = SplashScreen.getInstance().getDaoSession().getBankBranchDetailDataDao().queryBuilder()
                .where(BankBranchDetailDataDao.Properties.BankBranchCode.eq(bankBranchCode))
                .limit(1).unique().getIfscCode();
        return ifsc;
    }


    @OnClick(R.id.dateOfJoiningTv)
    void getDateFragment() {
        if (isShopKeeper.isEmpty() || isShopKeeper.equalsIgnoreCase("")) {
            Toast.makeText(this, getString(R.string.error_secc_no), Toast.LENGTH_SHORT).show();
        } else {
            DialogFragment dateFragment = new DatePicker(dateOfJoiningTv);
            dateFragment.show(getSupportFragmentManager(), "date picker");
            doj = dateOfJoiningTv.getText().toString();
            takAadhaarSelfiBtn.setVisibility(View.VISIBLE);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.saveDataBtn)
    void saveMemberDetail() {

        /**aadhar detail verification***/
        aadharUid = enterAadharEt.getText().toString();
        name = enterAadhaeNameET.getText().toString().trim().toUpperCase();
        gender = aadharGenderEt.getText().toString().trim();
        co = aadharFatherNameEt.getText().toString().trim().toUpperCase();
        yob = aadharYobEt.getText().toString().trim();
        completAddress = aadharAddressEt.getText().toString().trim();
        mobileNumber = aadharMobileNumberEt.getText().toString();

        accountNumber = accountNumberEt.getText().toString();
        confirmAccNumber = confirmAccountNumberEt.getText().toString();
        //pinCode=pincodeET.getText().toString().trim();
        if (aadharUid.isEmpty() || aadharUid.equalsIgnoreCase("")) {
            enterAadharEt.setError(getString(R.string.error_uidai_id));
        } else if (name.isEmpty() || name.equalsIgnoreCase("") || name.length() < 3) {
            enterAadhaeNameET.setError(getString(R.string.error_name));
        } else if (gender.isEmpty() || gender.equalsIgnoreCase("")) {
            aadharGenderEt.setError(getString(R.string.error_gender));
        } else if (co.isEmpty() || co.equalsIgnoreCase("") || co.length() < 3) {
            aadharFatherNameEt.setError(getString(R.string.error_Fname));
        } else if (yob.isEmpty() || yob.equalsIgnoreCase("")) {
            aadharYobEt.setError(getString(R.string.error_yob));
        } else if (completAddress.isEmpty() || completAddress.equalsIgnoreCase("")) {
            aadharAddressEt.setError(getString(R.string.error_address));
        } else if (mobileNumber.isEmpty() || mobileNumber.equalsIgnoreCase("")) {
            aadharMobileNumberEt.setError(getString(R.string.error_mobile));
            Toast.makeText(AddShgMemberActivity.this, getString(R.string.enter_mobile_number), Toast.LENGTH_SHORT).show();
        } else if (socialCatgory.equalsIgnoreCase("")) {
            Toast.makeText(AddShgMemberActivity.this, getString(R.string.select_social_category), Toast.LENGTH_SHORT).show();
        } else if (disablity.equalsIgnoreCase("")) {
            Toast.makeText(AddShgMemberActivity.this, R.string.disablity, Toast.LENGTH_SHORT).show();
        } else if (religon.equalsIgnoreCase("")) {
            Toast.makeText(AddShgMemberActivity.this, getString(R.string.religion), Toast.LENGTH_SHORT).show();
        } else if (pip.equalsIgnoreCase("")) {
            Toast.makeText(AddShgMemberActivity.this, getString(R.string.pip), Toast.LENGTH_SHORT).show();
        } else if (leader.equalsIgnoreCase("")) {
            Toast.makeText(AddShgMemberActivity.this, getString(R.string.leader), Toast.LENGTH_SHORT).show();
        } else if (education.equalsIgnoreCase("")) {
            Toast.makeText(AddShgMemberActivity.this, getString(R.string.education), Toast.LENGTH_SHORT).show();
        } else if (doj.equalsIgnoreCase("")) {
            Toast.makeText(AddShgMemberActivity.this, getString(R.string.date_of_joining), Toast.LENGTH_SHORT).show();
        } else if (imageSelfiByteArray == null) {
            Toast.makeText(AddShgMemberActivity.this, getString(R.string.toast_error_member_photo), Toast.LENGTH_SHORT).show();
        }
        /***varification of bank branch data******/

        else if (bankName.equalsIgnoreCase("")) {
            Toast.makeText(AddShgMemberActivity.this, getString(R.string.select_bank_name), Toast.LENGTH_SHORT).show();
        } else if (bankBranchName.equalsIgnoreCase("")) {
            Toast.makeText(AddShgMemberActivity.this, getString(R.string.select_bank_branch), Toast.LENGTH_SHORT).show();
        } else if (ifscCode.equalsIgnoreCase("")) {

        } else if (accountNumber.length() < 6 || accountNumber.isEmpty()) {
            Toast.makeText(AddShgMemberActivity.this, getString(R.string.fill_account_number), Toast.LENGTH_SHORT).show();
        } else if (confirmAccNumber.equalsIgnoreCase("") || accountNumber.isEmpty()) {
            Toast.makeText(AddShgMemberActivity.this, getString(R.string.please_confirm_account_number), Toast.LENGTH_SHORT).show();
        } else if (aadharSeeded.equalsIgnoreCase("")) {
            Toast.makeText(AddShgMemberActivity.this, getString(R.string.select_aadhar_seeded), Toast.LENGTH_SHORT).show();
        } else if (imageByteArray == null) {
            Toast.makeText(AddShgMemberActivity.this, getString(R.string.toast_error_passbook_photo), Toast.LENGTH_SHORT).show();
        } else if (imageSelfiByteArray == null) {
            Toast.makeText(AddShgMemberActivity.this, getString(R.string.toast_error_member_photo), Toast.LENGTH_SHORT).show();
        } else if (aadharYobEt.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(AddShgMemberActivity.this, getString(R.string.select_dateofbirth), Toast.LENGTH_SHORT).show();
        }
        /***varification Imsurance Data******/

        else if (pmjjy.equalsIgnoreCase("")) {
            Toast.makeText(AddShgMemberActivity.this, getString(R.string.select_pmjjy), Toast.LENGTH_SHORT).show();
        } else if (pmsby.equalsIgnoreCase("")) {
            Toast.makeText(AddShgMemberActivity.this, getString(R.string.select_pmsby), Toast.LENGTH_SHORT).show();
        } else if (lifeInsurance.equalsIgnoreCase("")) {
            Toast.makeText(AddShgMemberActivity.this, getString(R.string.select_pension), Toast.LENGTH_SHORT).show();
        } else if (pension.equalsIgnoreCase("")) {
            Toast.makeText(AddShgMemberActivity.this, getString(R.string.select_pension), Toast.LENGTH_SHORT).show();
        }/* else if (education.equalsIgnoreCase("OTHER")) {
            otherEducation = otherEduET.getText().toString().trim();
            if (otherEducation.equalsIgnoreCase("") || otherEducation.isEmpty() || otherEducation.equals(null)) {
                otherEduET.setError(getString(R.string.error_other_edu));
                Toast.makeText(AddShgMemberActivity.this, getString(R.string.other_education), Toast.LENGTH_SHORT).show();
            } else {
                if (socialCatgory.equalsIgnoreCase("ST")&& pvgtStatus.equalsIgnoreCase("")){
                    pvgtStatusSpinner.setError(getString(R.string.error_pvgt));
                }else pvgtStatus="N";
                saveMembersData(AddShgMemberActivity.this);

                *//**remove this intent after ll condition is ok for sync data**//*
         *//*  Intent intent = new Intent(AddShgMemberActivity.this, HomeActivity.class);
               startActivity(intent);*//*
            }
        }*/
        else {
            if (education.equalsIgnoreCase("OTHER")) {
                otherEducation = otherEduET.getText().toString().trim();
                if (otherEducation.equalsIgnoreCase("") || otherEducation.isEmpty() || otherEducation.equals(null)) {
                    otherEduET.setError(getString(R.string.error_other_edu));
                    Toast.makeText(AddShgMemberActivity.this, getString(R.string.other_education), Toast.LENGTH_SHORT).show();
                } else {
                    if (socialCatgory.equalsIgnoreCase("ST") && pvgtStatus.equalsIgnoreCase("")) {
                        pvgtStatusSpinner.setError(getString(R.string.error_pvgt));
                    } else {
                        if (fileSize<AppConstant.FILE_SIZE)
                            saveMembersData(AddShgMemberActivity.this);
                        else Toast.makeText(AddShgMemberActivity.this, getString(R.string.error_file_size), Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                if (socialCatgory.equalsIgnoreCase("ST") && pvgtStatus.equalsIgnoreCase("")) {
                    pvgtStatusSpinner.setError(getString(R.string.error_pvgt));
                } else {
                    if (fileSize<AppConstant.FILE_SIZE)
                        saveMembersData(AddShgMemberActivity.this);
                    else Toast.makeText(AddShgMemberActivity.this, getString(R.string.error_file_size), Toast.LENGTH_SHORT).show();

                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void saveMembersData(Context context) {
        if (pdfByteArray!=null){
        ShgMemberRegistrationSyncData shgMemberRegistrationSyncData = new ShgMemberRegistrationSyncData();

        /**in this table all enteraadhar
         *  card is add for check duplicate
         *  aadhar card and never clear this database
         *  untill application is unistalled*/
        AddMemberAadharDetail addMemberAadharDetail = new AddMemberAadharDetail();
        addMemberAadharDetail.setUidNumber(aadharUid);
        SplashScreen.getInstance().getDaoSession().getAddMemberAadharDetailDao().insert(addMemberAadharDetail);


        HashMap<String, String> charavalueForServerOT = new HashMap<String, String>();
        charavalueForServerOT.put("Buddhist", "B");
        charavalueForServerOT.put("Christian", "C");
        charavalueForServerOT.put("Hindu", "H");
        charavalueForServerOT.put("Jain", "J");
        charavalueForServerOT.put("Muslim", "M");
        charavalueForServerOT.put("Parsis", "P");
        charavalueForServerOT.put("Sikh", "S");
        charavalueForServerOT.put("Other", "O");
        charavalueForServerOT.put("POP", "POP");
        charavalueForServerOT.put("Poor", "Poor");
        charavalueForServerOT.put("Non-Poor", "NP");
        charavalueForServerOT.put("No", "N");
        charavalueForServerOT.put("SELF", "S");
        charavalueForServerOT.put("FAMILY MEMBER", "DM");

        HashMap<String, String> charValueForEduYN = new HashMap<String, String>();
        charValueForEduYN.put("Yes", "Y");
        charValueForEduYN.put("No", "N");


        //write condition if this all are avalable
        // memberUniqueCode = blockCode + "," + gpCode + "," + villageCode + "," + aadharUid;
        /**make uniq code you+aadhar uid
         *  same code send for check duplicate
         *  in duplicate api*/

        memberUniqueCode = villageCode + aadharUid + shgCode;

        AppUtils.getInstance().showLog("memberUniqueCode" + memberUniqueCode, AddShgMemberActivity.class);
        //write condition all details selected or not
        shgMemberRegistrationSyncData.setBlockcode(blockCode);
        shgMemberRegistrationSyncData.setGpcode(gpCode);
        shgMemberRegistrationSyncData.setVillagecode(villageCode);
        shgMemberRegistrationSyncData.setShgcode(shgCode);
        shgMemberRegistrationSyncData.setShgMemberuniqueCode(memberUniqueCode);
        shgMemberRegistrationSyncData.setAadharNumber(aadharUid);
        shgMemberRegistrationSyncData.setMemberName(name);
        shgMemberRegistrationSyncData.setMemberGender(gender);
        shgMemberRegistrationSyncData.setMemberFathername(co);
        shgMemberRegistrationSyncData.setMemberAddress(completAddress);
        shgMemberRegistrationSyncData.setPostalCode("NO");
        shgMemberRegistrationSyncData.setMembermobileNumber(mobileNumber);
        shgMemberRegistrationSyncData.setMemberYOB(yob);
        shgMemberRegistrationSyncData.setMamberDateOfJoining(doj);
        shgMemberRegistrationSyncData.setSocialCatagory(socialCatgory);
        shgMemberRegistrationSyncData.setDisablity(charavalueForServerOT.get(disablity));
        shgMemberRegistrationSyncData.setReligion(charavalueForServerOT.get(religon));
        shgMemberRegistrationSyncData.setPipCatagory(charavalueForServerOT.get(pip));
        shgMemberRegistrationSyncData.setLeader(leader);
        shgMemberRegistrationSyncData.setEducationStandard(education);
        shgMemberRegistrationSyncData.setBankName(bankName);
        shgMemberRegistrationSyncData.setBankNameCode(bankCode);
        shgMemberRegistrationSyncData.setBankBranch(bankBranchName);
        shgMemberRegistrationSyncData.setBankBranchCode(bankBranchCode);
        shgMemberRegistrationSyncData.setIfscCode(ifscCode);
        shgMemberRegistrationSyncData.setAccountNumber(accountNumber);
        shgMemberRegistrationSyncData.setAadharSeededAccount(charValueForEduYN.get(aadharSeeded));
        shgMemberRegistrationSyncData.setPmjjy(charValueForEduYN.get(pmjjy));
        shgMemberRegistrationSyncData.setPmsby(charValueForEduYN.get(pmsby));
        shgMemberRegistrationSyncData.setLifeInsurance(charValueForEduYN.get(lifeInsurance));
        shgMemberRegistrationSyncData.setPensionScheme(charValueForEduYN.get(pension));
        shgMemberRegistrationSyncData.setSeccNo("N");
        shgMemberRegistrationSyncData.setOtherEducation(otherEducation);
        shgMemberRegistrationSyncData.setIsShopKeeper(charValueForEduYN.get(isShopKeeper));
        shgMemberRegistrationSyncData.setDisablity(charavalueForServerOT.get(disablity));
        shgMemberRegistrationSyncData.setPassbookPhoto(imageByteArray);
        shgMemberRegistrationSyncData.setMemberPhoto(imageSelfiByteArray);
        shgMemberRegistrationSyncData.setConcentForm(pdfByteArray);

        //sync status is default 0 after sync its update to 1
        shgMemberRegistrationSyncData.setSyncStatus("0");
        shgMemberRegistrationSyncData.setScanStatus(scanStatus);
        shgMemberRegistrationSyncData.setPvgtStatus(pvgtStatus);

        AppUtils.getInstance().showLog("data" + shgMemberRegistrationSyncData, AddShgMemberActivity.class);
        SplashScreen.getInstance().getDaoSession().getShgMemberRegistrationSyncDataDao()
                .insert(shgMemberRegistrationSyncData);

        /** write api for check data
         * is duplicate or not if found
         * duplicate delet data in local
         * db and send again new data for sync*/
        if (AppUtils.isInternetOn(AddShgMemberActivity.this)) {
            ProgressDialog progressDialog = new ProgressDialog(AddShgMemberActivity.this);
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.setCancelable(false);
            progressDialog.show();
            /* apiForCheckDuplicateData(progressDialog, context);*/
            PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getCallingApi(), AppConstant.CALLING_API_ADD_MEMBER, AddShgMemberActivity.this);
            SyncData.getInstance().syncData(AddShgMemberActivity.this);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                    String ADD_MEMBER_API_VOLLEY_ERROR = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getAddMemberApiVolleyError(), AddShgMemberActivity.this);
                    if (ADD_MEMBER_API_VOLLEY_ERROR.contentEquals(AppConstant.ADD_MEMBER_API_VOLLEY_ERROR)) {
                        PreferenceFactory.getInstance().removeSharedPrefrencesData(PreferenceManager.getAddMemberApiVolleyError(), AddShgMemberActivity.this);
                        DialogFactory.getInstance().showAlertDialog(AddShgMemberActivity.this, 0, getString(R.string.info), getString(R.string.ADD_MEMBER_API_VOLLEY_ERROR),
                                getString(R.string.ok), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(@NonNull DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getPrefKeyShgMemberStatus()
                                                , "goToMemberList", AddShgMemberActivity.this);
                                        Intent intent = new Intent(AddShgMemberActivity.this, HomeActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }, false);
                    } else {
                        // updateShgMemberBackUpFile(AddShgMemberActivity.this);
                        Toast.makeText(getApplicationContext(), getString(R.string.data_sync_msg), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(AddShgMemberActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }

                }
            }, 8000);

        } else {
            updateShgMemberBackUpFile(AddShgMemberActivity.this);
            Toast.makeText(getApplicationContext(), getString(R.string.data_save_msg), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(AddShgMemberActivity.this, HomeActivity.class);
            startActivity(intent);
        }
        }else {
            Toast.makeText(AddShgMemberActivity.this, getString(R.string.error_uploade_concent), Toast.LENGTH_SHORT).show();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void updateShgMemberBackUpFile(@NonNull Context context) {
        try {
            SyncData syncData = SyncData.getInstance();
            syncData.getLogInfo(context);

            FileUtility.getInstance().createFileInMemory(FileManager.getInstance()
                            .getPathDetails(context)
                    , AppConstant.ADD_MEMBER_FILE_NAME, new Cryptography().encrypt(syncData.getShgMemberJsonData(context).toString()));
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

    @OnClick(R.id.aadharScannerIV1)
    void aadhaarScanerImageView() {
        IntentIntegrator integrator = new IntentIntegrator(AddShgMemberActivity.this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setPrompt(getString(R.string.scanBarCode));
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        // integrator.setTimeout(30000);
        integrator.setTorchEnabled(true);
        integrator.initiateScan();

    }

    /**
     * take aadhar member selfi photo
     **/
    @OnClick(R.id.takAadhaarSelfiBtn)
    void clickMemberSelfi() {
        aadharUid = enterAadharEt.getText().toString().trim();
        if (aadharUid != null && !aadharUid.isEmpty()) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,
                    AppConstant.CAPTURE_IMAGE_ACTIVITY_SELFI_REQUEST_CODE);
        } else {
            Toast.makeText(AddShgMemberActivity.this, getString(R.string.fillAddharDetail), Toast.LENGTH_SHORT).show();
        }


    }

    @OnClick(R.id.takeAgainSelfiBtn)
    void clickAgainMemberSelfi() {
        aadharUid = enterAadharEt.getText().toString().trim();
        if (aadharUid != null && !aadharUid.isEmpty()) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,
                    AppConstant.CAPTURE_IMAGE_ACTIVITY_SELFI_REQUEST_CODE);
        } else {
            Toast.makeText(AddShgMemberActivity.this, getString(R.string.fillAddharDetail), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * take Passbook  photo
     **/
    @OnClick(R.id.takePassbookPhotoBtn)
    void clickPassbookPhoto() {
        accountNumber = accountNumberEt.getText().toString().trim();
        confirmAccNumber = confirmAccountNumberEt.getText().toString().trim();
        if ((accountNumber != null && !accountNumber.isEmpty()) && (confirmAccNumber != null && !confirmAccNumber.isEmpty())) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,
                    AppConstant.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        } else {
            Toast.makeText(AddShgMemberActivity.this, getString(R.string.fillAccountDetail), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.takeAgainPassbookBtn)
    void clickAgainPassbookPhoto() {
        accountNumber = accountNumberEt.getText().toString().trim();
        confirmAccNumber = confirmAccountNumberEt.getText().toString().trim();
        if ((accountNumber != null && !accountNumber.isEmpty()) && (confirmAccNumber != null && !confirmAccNumber.isEmpty())) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,
                    AppConstant.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        } else {
            Toast.makeText(AddShgMemberActivity.this, getString(R.string.fillAccountDetail), Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFocus(int i) {
        switch (i) {
            case 1:
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.slide_up);
                accountDetailLinearLayout.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        accountDetailLinearLayout.setVisibility(View.GONE);

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                bankBranchCode = "";
                bankBranchName = "";
                ifscCode = "";
                ifcsTv.setText(ifscCode);

                accountNumber = "";
                confirmAccNumber = "";
                bankAccLength = "";
                aadharSeeded = "";

                accountNumberEt.setText("");
                confirmAccountNumberEt.setText("");
                accNumberTIL.setHelperText("");
                accNumberTIL.setCounterEnabled(false);
                accNumberTIL.setCounterMaxLength(0);

                spinner_BankBranchSpin.setFocusableInTouchMode(false);
                bankBranchArray = new String[0];
                bankBranchesDataListItems.clear();
                spinner_BankBranchSpin.setText("");
                ArrayAdapter<String> bankBranchNameAdapter1 = new ArrayAdapter<String>(AddShgMemberActivity.this,
                        R.layout.spinner_textview, bankBranchArray);
                spinner_BankBranchSpin.setAdapter(bankBranchNameAdapter1);

                aadharSeededSpinner.setFocusableInTouchMode(false);
                aadharSeededArray = new String[0];
                aadharSeededSpinner.setText("");
                ArrayAdapter<String> aadharSeededAdapter = new ArrayAdapter<String>(AddShgMemberActivity.this,
                        R.layout.spinner_textview, aadharSeededArray);
                aadharSeededSpinner.setAdapter(aadharSeededAdapter);
                break;
            case 2:
                accountNumber = "";
                confirmAccNumber = "";
                aadharSeeded = "";

                accountNumberEt.setText("");
                confirmAccountNumberEt.setText("");
                aadharSeededSpinner.setText("");

                break;
            case 3:

                break;
            case 4:

                break;
            case 5:

                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppConstant.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            /**request code for passbook image**/
            try {
            if (resultCode == Activity.RESULT_OK) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        photoLinearLayout.setVisibility(View.VISIBLE);
                        takePassbookPhotoBtn.setVisibility(View.GONE);
                        Bitmap bmp = (Bitmap) data.getExtras().get("data");
                        passbookImageView.setImageBitmap(bmp);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.PNG, IMAGE_COMPRESS_QLTY, baos);
                        imageByteArray = baos.toByteArray();
                        AppUtils.getInstance().showLog("byteArray" + imageByteArray, AadharAccountActivity.class);
                    }
                }, 1000);
            }
            } catch (Exception e) {
                Toast.makeText(this, getString(R.string.toast_file_upload_error), Toast.LENGTH_LONG).show();
            }

        } else if (requestCode == AppConstant.CAPTURE_IMAGE_ACTIVITY_SELFI_REQUEST_CODE) {
            /**request code for member image**/
            try {
            if (resultCode == Activity.RESULT_OK) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        aadharPhotoLinearLayout.setVisibility(View.VISIBLE);
                        takAadhaarSelfiBtn.setVisibility(View.GONE);
                        Bitmap bmp = (Bitmap) data.getExtras().get("data");
                        aadharImageView.setImageBitmap(bmp);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.PNG, IMAGE_COMPRESS_QLTY, baos);
                        imageSelfiByteArray = baos.toByteArray();
                        AppUtils.getInstance().showLog("byteArray" + imageSelfiByteArray, AadharAccountActivity.class);
                    }
                }, 1000);
            }

            } catch (Exception e) {
                Toast.makeText(this, getString(R.string.toast_file_upload_error), Toast.LENGTH_LONG).show();
            }

        } else if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            /**request code for concent image**/
            try {
                Bitmap bmp = (Bitmap) data.getExtras().get("data");
                fileNameTV.setImageBitmap(bmp);
                concent_textTV.setVisibility(View.GONE);
                fileNameTV.setVisibility(View.VISIBLE);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, IMAGE_COMPRESS_QLTY, baos);
                pdfByteArray = baos.toByteArray();
                AppUtils.getInstance().showLog("byteArray" + pdfByteArray, AadharAccountActivity.class);

                AppUtils.getInstance().showLog("compression is done ", AadharAccountActivity.class);


            } catch (Exception e) {
                Toast.makeText(this, getString(R.string.toast_file_upload_error), Toast.LENGTH_LONG).show();
            }

        } else {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
          //  AppUtils.getInstance().showLog("INTENT_RESULT" + result.toString(), AadharAccountActivity.class);
            if (result != null) {
                if (result.getContents() == null) {
                    Toast.makeText(AddShgMemberActivity.this, getString(R.string.canceld), Toast.LENGTH_LONG).show();

                    aadharDetailLinearLayout.setVisibility(View.VISIBLE);
                    enterAadharEt.setEnabled(true);
                    enterAadhaeNameET.setEnabled(true);
                    aadharGenderEt.setEnabled(true);
                    aadharFatherNameEt.setEnabled(true);
                    aadharYobEt.setEnabled(true);
                    aadharAddressEt.setEnabled(true);

                    enterAadharEt.setFocusable(true);
                    enterAadhaeNameET.setFocusable(true);
                    aadharGenderEt.setFocusable(true);
                    aadharFatherNameEt.setFocusable(true);
                    aadharYobEt.setFocusable(true);
                    aadharAddressEt.setFocusable(true);

                    aadharYobEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (hasFocus) {

                                int mYear, mMonth, mDay;
                                if (v == aadharYobEt) {
                                    final Calendar c = Calendar.getInstance();
                                    mYear = c.get(Calendar.YEAR);
                                    mMonth = c.get(Calendar.MONTH);
                                    mDay = c.get(Calendar.DAY_OF_MONTH);


                                    DatePickerDialog datePickerDialog = new DatePickerDialog(AddShgMemberActivity.this,
                                            new DatePickerDialog.OnDateSetListener() {
                                                @Override
                                                public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                                                    String day = "";
                                                    if (dayOfMonth < 10)
                                                        day = "0" + dayOfMonth;
                                                    else day = String.valueOf(dayOfMonth);
                                                    AppUtils.getInstance().showLog("daywithformate" + day, AddShgMemberActivity.class);
                                                    month+=1;
                                                    String monthc = "";
                                                    if (month < 10)
                                                        monthc = "0" + month;
                                                    else monthc = String.valueOf(month);
                                                    AppUtils.getInstance().showLog("monthwithformate" + monthc, AddShgMemberActivity.class);

                                                    aadharYobEt.setText(day + "-" + monthc + "-" + year);
                                                    yob = aadharYobEt.getText().toString().trim();
                                                }
                                            }, mYear - 18, mMonth, mDay);
                                    c.set(mYear - 18, mMonth, mDay);
                                    long value = c.getTimeInMillis();
                                    datePickerDialog.getDatePicker().setMaxDate(value);
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


                                DatePickerDialog datePickerDialog = new DatePickerDialog(AddShgMemberActivity.this,
                                        new DatePickerDialog.OnDateSetListener() {
                                            @Override
                                            public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                                                String day = "";
                                                if (dayOfMonth < 10)
                                                    day = "0" + dayOfMonth;
                                                else day = String.valueOf(dayOfMonth);
                                                AppUtils.getInstance().showLog("daywithformate" + day, AddShgMemberActivity.class);

                                                String monthc = "";
                                                if (month < 10)
                                                    monthc = "0" + (month + 1);
                                                else monthc = String.valueOf(month);
                                                AppUtils.getInstance().showLog("monthwithformate" + monthc, AddShgMemberActivity.class);

                                                aadharYobEt.setText(day + "-" + monthc + "-" + year);
                                                yob = aadharYobEt.getText().toString().trim();
                                            }
                                        }, mYear - 18, mMonth, mDay);
                                c.set(mYear - 18, mMonth, mDay);
                                long value = c.getTimeInMillis();
                                datePickerDialog.getDatePicker().setMaxDate(value);
                                datePickerDialog.show();
                            }
                        }
                    });

                    cardScanStatus = "1";
                    scanStatus = "N";

                } else {
                    XmlPullParserFactory pullParserFactory;
                    try {
                        pullParserFactory = XmlPullParserFactory.newInstance();
                        parser = pullParserFactory.newPullParser();
                        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                        parser.setInput(new StringReader(result.getContents()));
                        processParsing(parser);
                    } catch (Exception e) {
                        Dialog dialog = new Dialog(AddShgMemberActivity.this);
                        dialog.setContentView(R.layout.my_qr_errorcode_dialog);
                        Button ok = (Button) dialog.findViewById(R.id.buttonOk);
                        dialog.setCancelable(false);
                        dialog.show();
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                //visibility on for detailed layout
                                aadharDetailLinearLayout.setVisibility(View.VISIBLE);
                            }
                        });
                        // Toast.makeText(AddShgMemberActivity.this, "excpe" + e, Toast.LENGTH_LONG).show();
                    }
                    AppUtils.getInstance().showLog("response", AadharAccountActivity.class);
                }
            } else {
                Toast.makeText(AddShgMemberActivity.this, getString(R.string.not_scaned), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void processParsing(@NonNull XmlPullParser parser) throws IOException, XmlPullParserException {
        int eventType = parser.getEventType();
        AppUtils.getInstance().showLog("eventType" + eventType, AadharAccountActivity.class);

        while (eventType != XmlPullParser.END_DOCUMENT) {

            if (eventType == XmlPullParser.START_DOCUMENT) {

            } else if (eventType == XmlPullParser.START_TAG) {

                AppUtils.getInstance().showLog("Start tag " + parser.getName(), AadharAccountActivity.class);

                if (parser.getName().equalsIgnoreCase("PrintLetterBarcodeData")) {
                    uid = parser.getAttributeValue(null, "uid");
                    if (uid == null) {
                        uid = "";
                    }
                    aadharUid = enterAadharEt.getText().toString();
                    if (aadharUid == null || aadharUid.equalsIgnoreCase("")) {
                        enterAadharEt.setText(uid);
                    } else {
                        if (aadharUid.equalsIgnoreCase(uid)) {
                            aadharUid = uid;
                            progressDialog.show();
                            name = parser.getAttributeValue(null, "name");
                            if (name == null)
                                name = "";
                            gender = parser.getAttributeValue(null, "gender");
                            if (gender == null)
                                gender = "";
                            co = parser.getAttributeValue(null, "co");
                            if (co == null)
                                co = "";
                            yob = parser.getAttributeValue(null, "yob");
                            if (yob == null)
                                yob = "";
                            house = parser.getAttributeValue(null, "house");
                            if (house == null)
                                house = "";
                            loc = parser.getAttributeValue(null, "loc");
                            if (loc == null)
                                loc = "";
                            vtc = parser.getAttributeValue(null, "vtc");
                            if (vtc == null)
                                vtc = "";
                            dist = parser.getAttributeValue(null, "dist");
                            if (dist == null)
                                dist = "";
                            state = parser.getAttributeValue(null, "state");
                            if (state == null)
                                state = "";
                            pc = parser.getAttributeValue(null, "pc");
                            if (pc == null)
                                pc = "";
                            dob = parser.getAttributeValue(null, "dob");
                            if (dob == null) {
                                dob = "";
                                //set defauly msg
                            } else {
                                // set date get from scanner
                            }

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    progressDialog.dismiss();
                                    aadharDetailLinearLayout.setVisibility(View.VISIBLE);
                                    enterAadhaeNameET.setText(name);
                                    aadharGenderEt.setText(gender);
                                    aadharFatherNameEt.setText(co);
                                    aadharYobEt.setText(yob);
                                    completAddress = house + ", " + loc + ", " + vtc + ", " + dist + ", " + state + ", " + pc;
                                    aadharAddressEt.setText(completAddress);


                                    enterAadharEt.setEnabled(false);
                                    enterAadhaeNameET.setEnabled(false);
                                    aadharGenderEt.setEnabled(false);
                                    aadharFatherNameEt.setEnabled(false);
                                    aadharYobEt.setEnabled(false);
                                    aadharAddressEt.setEnabled(false);

                                    enterAadhaeNameET.setFocusable(false);
                                    aadharGenderEt.setFocusable(false);
                                    aadharFatherNameEt.setFocusable(false);
                                    aadharYobEt.setFocusable(false);
                                    aadharAddressEt.setFocusable(false);
                                    scanStatus = "Y";
                                }
                            }, 3000); // 3000

                        } else {
                            //show a custom dialog for enter wrong pasword\
                            //Toast.makeText(getApplicationContext(), getString(R.string.enterErongAadhar), Toast.LENGTH_SHORT).show();
                            DialogFactory.getInstance().showAlertDialog(AddShgMemberActivity.this, 0, getString(R.string.info), getString(R.string.enterErongAadhar),
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


    public void showSnacks(View v,String msg) {
        //  View snackBarContView=LayoutInflater.from(context).inflate(R.layout.spinner_textview,null,false);

        Snackbar snackBar = Snackbar.make(v, msg, Snackbar.LENGTH_LONG).setAction("",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
        snackBar.setBackgroundTint(getResources().getColor(R.color.colorWhite));
        snackBar.setTextColor(getResources().getColor(R.color.colorRed));

        snackBar.show();
    }


}

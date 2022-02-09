// Generated code from Butter Knife. Do not modify!
package com.example.aadharscanner.activites;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.aadharscanner.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddShgMemberActivity_ViewBinding implements Unbinder {
  private AddShgMemberActivity target;

  private View view7f090017;

  private View view7f09009e;

  private View view7f09017b;

  private View view7f0901cd;

  private View view7f0901d0;

  private View view7f0901d3;

  private View view7f0901cf;

  private View view7f09005f;

  @UiThread
  public AddShgMemberActivity_ViewBinding(AddShgMemberActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AddShgMemberActivity_ViewBinding(final AddShgMemberActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.aadharScannerIV1, "method 'aadhaarScanerImageView'");
    target.aadharScannerIV = Utils.castView(view, R.id.aadharScannerIV1, "field 'aadharScannerIV'", ImageView.class);
    view7f090017 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.aadhaarScanerImageView();
      }
    });
    target.aadharImageView = Utils.findOptionalViewAsType(source, R.id.aadharImageView, "field 'aadharImageView'", ImageView.class);
    target.passbookImageView = Utils.findOptionalViewAsType(source, R.id.passbookImageView, "field 'passbookImageView'", ImageView.class);
    target.enterAadharEt = Utils.findOptionalViewAsType(source, R.id.enterAadharEt, "field 'enterAadharEt'", EditText.class);
    target.enterAadhaeNameET = Utils.findOptionalViewAsType(source, R.id.enterAadhaeNameET, "field 'enterAadhaeNameET'", EditText.class);
    target.aadharGenderEt = Utils.findOptionalViewAsType(source, R.id.aadharGenderEt, "field 'aadharGenderEt'", EditText.class);
    target.aadharFatherNameEt = Utils.findOptionalViewAsType(source, R.id.aadharFatherNameEt, "field 'aadharFatherNameEt'", EditText.class);
    target.aadharYobEt = Utils.findOptionalViewAsType(source, R.id.aadharYobEt, "field 'aadharYobEt'", EditText.class);
    target.aadharAddressEt = Utils.findOptionalViewAsType(source, R.id.aadharAddressEt, "field 'aadharAddressEt'", EditText.class);
    target.accountNumberEt = Utils.findOptionalViewAsType(source, R.id.accountNumberEt, "field 'accountNumberEt'", TextInputEditText.class);
    target.confirmAccountNumberEt = Utils.findOptionalViewAsType(source, R.id.confirmAccountNumberEt, "field 'confirmAccountNumberEt'", TextInputEditText.class);
    target.aadharMobileNumberEt = Utils.findOptionalViewAsType(source, R.id.aadharMobileNumberEt, "field 'aadharMobileNumberEt'", EditText.class);
    target.socialCatagorySpinner = Utils.findOptionalViewAsType(source, R.id.socialCatagorySpinner, "field 'socialCatagorySpinner'", MaterialBetterSpinner.class);
    target.pvgtStatusSpinner = Utils.findOptionalViewAsType(source, R.id.pvgtStatusSpinner, "field 'pvgtStatusSpinner'", MaterialBetterSpinner.class);
    target.disablitySpinner = Utils.findOptionalViewAsType(source, R.id.disablitySpinner, "field 'disablitySpinner'", MaterialBetterSpinner.class);
    target.religionSpinner = Utils.findOptionalViewAsType(source, R.id.religionSpinner, "field 'religionSpinner'", MaterialBetterSpinner.class);
    target.shoopkeeperMBS = Utils.findOptionalViewAsType(source, R.id.shoopkeeperMBS, "field 'shoopkeeperMBS'", MaterialBetterSpinner.class);
    target.seccMSB = Utils.findOptionalViewAsType(source, R.id.seccMSB, "field 'seccMSB'", MaterialBetterSpinner.class);
    target.pipSpinner = Utils.findOptionalViewAsType(source, R.id.pipSpinner, "field 'pipSpinner'", MaterialBetterSpinner.class);
    target.leaderSpinner = Utils.findOptionalViewAsType(source, R.id.leaderSpinner, "field 'leaderSpinner'", MaterialBetterSpinner.class);
    target.educationSpinner = Utils.findOptionalViewAsType(source, R.id.educationSpinner, "field 'educationSpinner'", MaterialBetterSpinner.class);
    target.pmjjySpinner = Utils.findOptionalViewAsType(source, R.id.pmjjySpinner, "field 'pmjjySpinner'", MaterialBetterSpinner.class);
    target.pmsbySpinner = Utils.findOptionalViewAsType(source, R.id.pmsbySpinner, "field 'pmsbySpinner'", MaterialBetterSpinner.class);
    target.lifeInsuranceSpinner = Utils.findOptionalViewAsType(source, R.id.lifeInsuranceSpinner, "field 'lifeInsuranceSpinner'", MaterialBetterSpinner.class);
    target.pensionSpinner = Utils.findOptionalViewAsType(source, R.id.pensionSpinner, "field 'pensionSpinner'", MaterialBetterSpinner.class);
    target.spinner_BankNameSpin = Utils.findOptionalViewAsType(source, R.id.spinner_BankNameSpin, "field 'spinner_BankNameSpin'", MaterialBetterSpinner.class);
    target.spinner_BankBranchSpin = Utils.findOptionalViewAsType(source, R.id.spinner_BankBranchSpin, "field 'spinner_BankBranchSpin'", MaterialBetterSpinner.class);
    target.aadharSeededSpinner = Utils.findOptionalViewAsType(source, R.id.aadharSeededSpinner, "field 'aadharSeededSpinner'", MaterialBetterSpinner.class);
    view = Utils.findRequiredView(source, R.id.dateOfJoiningTv, "method 'getDateFragment'");
    target.dateOfJoiningTv = Utils.castView(view, R.id.dateOfJoiningTv, "field 'dateOfJoiningTv'", TextView.class);
    view7f09009e = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.getDateFragment();
      }
    });
    target.member_ShgCodeTv = Utils.findOptionalViewAsType(source, R.id.member_ShgCodeTv, "field 'member_ShgCodeTv'", TextView.class);
    target.member_ShgNameTv = Utils.findOptionalViewAsType(source, R.id.member_ShgNameTv, "field 'member_ShgNameTv'", TextView.class);
    target.member_VillageNameTv = Utils.findOptionalViewAsType(source, R.id.member_VillageNameTv, "field 'member_VillageNameTv'", TextView.class);
    target.member_GpNameTv = Utils.findOptionalViewAsType(source, R.id.member_GpNameTv, "field 'member_GpNameTv'", TextView.class);
    target.otherEduET = Utils.findOptionalViewAsType(source, R.id.otherEduET, "field 'otherEduET'", TextView.class);
    target.otherEduTIL = Utils.findOptionalViewAsType(source, R.id.otherEduTIL, "field 'otherEduTIL'", TextInputLayout.class);
    target.ifcsTv = Utils.findOptionalViewAsType(source, R.id.ifcsTv, "field 'ifcsTv'", EditText.class);
    target.aadharDetailLinearLayout = Utils.findOptionalViewAsType(source, R.id.detailLinearLayout, "field 'aadharDetailLinearLayout'", LinearLayout.class);
    target.accountDetailLinearLayout = Utils.findOptionalViewAsType(source, R.id.accDetailLinearLayout, "field 'accountDetailLinearLayout'", LinearLayout.class);
    target.aadharPhotoLinearLayout = Utils.findOptionalViewAsType(source, R.id.aadharPhotoLinearLayout, "field 'aadharPhotoLinearLayout'", LinearLayout.class);
    target.photoLinearLayout = Utils.findOptionalViewAsType(source, R.id.photoLinearLayout, "field 'photoLinearLayout'", LinearLayout.class);
    target.accNumberTIL = Utils.findOptionalViewAsType(source, R.id.accNumberTIL, "field 'accNumberTIL'", TextInputLayout.class);
    view = Utils.findRequiredView(source, R.id.saveDataBtn, "method 'saveMemberDetail'");
    target.saveDataBtn = Utils.castView(view, R.id.saveDataBtn, "field 'saveDataBtn'", TextView.class);
    view7f09017b = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.saveMemberDetail();
      }
    });
    view = Utils.findRequiredView(source, R.id.takAadhaarSelfiBtn, "method 'clickMemberSelfi'");
    target.takAadhaarSelfiBtn = Utils.castView(view, R.id.takAadhaarSelfiBtn, "field 'takAadhaarSelfiBtn'", Button.class);
    view7f0901cd = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickMemberSelfi();
      }
    });
    view = Utils.findRequiredView(source, R.id.takeAgainSelfiBtn, "method 'clickAgainMemberSelfi'");
    target.takeAgainSelfiBtn = Utils.castView(view, R.id.takeAgainSelfiBtn, "field 'takeAgainSelfiBtn'", Button.class);
    view7f0901d0 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickAgainMemberSelfi();
      }
    });
    view = Utils.findRequiredView(source, R.id.takePassbookPhotoBtn, "method 'clickPassbookPhoto'");
    target.takePassbookPhotoBtn = Utils.castView(view, R.id.takePassbookPhotoBtn, "field 'takePassbookPhotoBtn'", Button.class);
    view7f0901d3 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickPassbookPhoto();
      }
    });
    view = Utils.findRequiredView(source, R.id.takeAgainPassbookBtn, "method 'clickAgainPassbookPhoto'");
    target.takeAgainPassbookBtn = Utils.castView(view, R.id.takeAgainPassbookBtn, "field 'takeAgainPassbookBtn'", Button.class);
    view7f0901cf = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickAgainPassbookPhoto();
      }
    });
    view = Utils.findRequiredView(source, R.id.backBtn, "method 'onBackClick'");
    target.backBtn = Utils.castView(view, R.id.backBtn, "field 'backBtn'", TextView.class);
    view7f09005f = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onBackClick();
      }
    });
    target.upload_concentLL = Utils.findOptionalViewAsType(source, R.id.upload_concentLL, "field 'upload_concentLL'", LinearLayout.class);
    target.fileNameTV = Utils.findOptionalViewAsType(source, R.id.fileNameTV, "field 'fileNameTV'", ImageView.class);
    target.concent_textTV = Utils.findOptionalViewAsType(source, R.id.concent_textTV, "field 'concent_textTV'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AddShgMemberActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.aadharScannerIV = null;
    target.aadharImageView = null;
    target.passbookImageView = null;
    target.enterAadharEt = null;
    target.enterAadhaeNameET = null;
    target.aadharGenderEt = null;
    target.aadharFatherNameEt = null;
    target.aadharYobEt = null;
    target.aadharAddressEt = null;
    target.accountNumberEt = null;
    target.confirmAccountNumberEt = null;
    target.aadharMobileNumberEt = null;
    target.socialCatagorySpinner = null;
    target.pvgtStatusSpinner = null;
    target.disablitySpinner = null;
    target.religionSpinner = null;
    target.shoopkeeperMBS = null;
    target.seccMSB = null;
    target.pipSpinner = null;
    target.leaderSpinner = null;
    target.educationSpinner = null;
    target.pmjjySpinner = null;
    target.pmsbySpinner = null;
    target.lifeInsuranceSpinner = null;
    target.pensionSpinner = null;
    target.spinner_BankNameSpin = null;
    target.spinner_BankBranchSpin = null;
    target.aadharSeededSpinner = null;
    target.dateOfJoiningTv = null;
    target.member_ShgCodeTv = null;
    target.member_ShgNameTv = null;
    target.member_VillageNameTv = null;
    target.member_GpNameTv = null;
    target.otherEduET = null;
    target.otherEduTIL = null;
    target.ifcsTv = null;
    target.aadharDetailLinearLayout = null;
    target.accountDetailLinearLayout = null;
    target.aadharPhotoLinearLayout = null;
    target.photoLinearLayout = null;
    target.accNumberTIL = null;
    target.saveDataBtn = null;
    target.takAadhaarSelfiBtn = null;
    target.takeAgainSelfiBtn = null;
    target.takePassbookPhotoBtn = null;
    target.takeAgainPassbookBtn = null;
    target.backBtn = null;
    target.upload_concentLL = null;
    target.fileNameTV = null;
    target.concent_textTV = null;

    view7f090017.setOnClickListener(null);
    view7f090017 = null;
    view7f09009e.setOnClickListener(null);
    view7f09009e = null;
    view7f09017b.setOnClickListener(null);
    view7f09017b = null;
    view7f0901cd.setOnClickListener(null);
    view7f0901cd = null;
    view7f0901d0.setOnClickListener(null);
    view7f0901d0 = null;
    view7f0901d3.setOnClickListener(null);
    view7f0901d3 = null;
    view7f0901cf.setOnClickListener(null);
    view7f0901cf = null;
    view7f09005f.setOnClickListener(null);
    view7f09005f = null;
  }
}

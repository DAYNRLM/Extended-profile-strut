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
import androidx.cardview.widget.CardView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.aadharscanner.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AadharAccountActivity_ViewBinding implements Unbinder {
  private AadharAccountActivity target;

  private View view7f090017;

  private View view7f0901bd;

  private View view7f0901bc;

  private View view7f0901d4;

  private View view7f0901ce;

  private View view7f0901cd;

  private View view7f0901d0;

  @UiThread
  public AadharAccountActivity_ViewBinding(AadharAccountActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AadharAccountActivity_ViewBinding(final AadharAccountActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.aadharScannerIV1, "method 'imageView'");
    target.aadharScannerIV = Utils.castView(view, R.id.aadharScannerIV1, "field 'aadharScannerIV'", ImageView.class);
    view7f090017 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.imageView();
      }
    });
    target.passbookImageView = Utils.findOptionalViewAsType(source, R.id.passbookImageView, "field 'passbookImageView'", ImageView.class);
    target.aadharImageView = Utils.findOptionalViewAsType(source, R.id.aadharImageView, "field 'aadharImageView'", ImageView.class);
    target.enterAadharEt = Utils.findOptionalViewAsType(source, R.id.enterAadharEt, "field 'enterAadharEt'", EditText.class);
    target.enterAadhaeNameET = Utils.findOptionalViewAsType(source, R.id.enterAadhaeNameET, "field 'enterAadhaeNameET'", EditText.class);
    target.aadharGenderEt = Utils.findOptionalViewAsType(source, R.id.aadharGenderEt, "field 'aadharGenderEt'", EditText.class);
    target.aadharFatherNameEt = Utils.findOptionalViewAsType(source, R.id.aadharFatherNameEt, "field 'aadharFatherNameEt'", EditText.class);
    target.aadharYobEt = Utils.findOptionalViewAsType(source, R.id.aadharYobEt, "field 'aadharYobEt'", EditText.class);
    target.aadharAddressEt = Utils.findOptionalViewAsType(source, R.id.aadharAddressEt, "field 'aadharAddressEt'", EditText.class);
    target.aadharMobileNumberEt = Utils.findOptionalViewAsType(source, R.id.aadharMobileNumberEt, "field 'aadharMobileNumberEt'", EditText.class);
    target.aadharShgNameTv = Utils.findOptionalViewAsType(source, R.id.aadharShgNameTv, "field 'aadharShgNameTv'", TextView.class);
    target.aadharShgMemberNameTv = Utils.findOptionalViewAsType(source, R.id.aadharShgMemberNameTv, "field 'aadharShgMemberNameTv'", TextView.class);
    target.locationTv = Utils.findOptionalViewAsType(source, R.id.locationTv, "field 'locationTv'", TextView.class);
    target.aadhar_link_status = Utils.findOptionalViewAsType(source, R.id.aadhar_link_status, "field 'aadhar_link_status'", TextView.class);
    target.bank_account_link_status = Utils.findOptionalViewAsType(source, R.id.bank_account_link_status, "field 'bank_account_link_status'", TextView.class);
    target.aadharDetailLinearLayout = Utils.findOptionalViewAsType(source, R.id.detailLinearLayout, "field 'aadharDetailLinearLayout'", LinearLayout.class);
    target.accountDetailLinearLayout = Utils.findOptionalViewAsType(source, R.id.accDetailLinearLayout, "field 'accountDetailLinearLayout'", LinearLayout.class);
    target.photoLinearLayout = Utils.findOptionalViewAsType(source, R.id.photoLinearLayout, "field 'photoLinearLayout'", LinearLayout.class);
    target.aadharPhotoLinearLayout = Utils.findOptionalViewAsType(source, R.id.aadharPhotoLinearLayout, "field 'aadharPhotoLinearLayout'", LinearLayout.class);
    target.accDetailCardView = Utils.findOptionalViewAsType(source, R.id.accDetailCardView, "field 'accDetailCardView'", CardView.class);
    target.aadharDetailCardView = Utils.findOptionalViewAsType(source, R.id.aadharDetailCardView, "field 'aadharDetailCardView'", CardView.class);
    target.aadharScanHeaderCV = Utils.findOptionalViewAsType(source, R.id.aadharScanHeaderCV, "field 'aadharScanHeaderCV'", CardView.class);
    target.bankDetailsHeaderCV = Utils.findOptionalViewAsType(source, R.id.bankDetailsHeaderCV, "field 'bankDetailsHeaderCV'", CardView.class);
    target.spinner_BankTypeSpin = Utils.findOptionalViewAsType(source, R.id.spinner_BankTypeSpin, "field 'spinner_BankTypeSpin'", MaterialBetterSpinner.class);
    target.spinner_BankNameSpin = Utils.findOptionalViewAsType(source, R.id.spinner_BankNameSpin, "field 'spinner_BankNameSpin'", MaterialBetterSpinner.class);
    target.spinner_BankBranchSpin = Utils.findOptionalViewAsType(source, R.id.spinner_BankBranchSpin, "field 'spinner_BankBranchSpin'", MaterialBetterSpinner.class);
    target.ifscTextView = Utils.findOptionalViewAsType(source, R.id.ifcsTv, "field 'ifscTextView'", EditText.class);
    target.accountNumberEt = Utils.findOptionalViewAsType(source, R.id.accountNumberEt, "field 'accountNumberEt'", TextInputEditText.class);
    target.accNumberTIL = Utils.findOptionalViewAsType(source, R.id.accNumberTIL, "field 'accNumberTIL'", TextInputLayout.class);
    target.confirmAccountNumberEt = Utils.findOptionalViewAsType(source, R.id.confirmAccountNumberEt, "field 'confirmAccountNumberEt'", TextInputEditText.class);
    view = Utils.findRequiredView(source, R.id.submitAccountBtn, "method 'SubmitAccountDetail'");
    target.submitAccountBtn = Utils.castView(view, R.id.submitAccountBtn, "field 'submitAccountBtn'", Button.class);
    view7f0901bd = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.SubmitAccountDetail();
      }
    });
    view = Utils.findRequiredView(source, R.id.submitAadharBtn, "method 'submitAadharDetail'");
    target.submitAadharBtn = Utils.castView(view, R.id.submitAadharBtn, "field 'submitAadharBtn'", Button.class);
    view7f0901bc = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.submitAadharDetail();
      }
    });
    view = Utils.findRequiredView(source, R.id.takePhotoBtn, "method 'takePaasbookPhoto'");
    target.takePhotoBtn = Utils.castView(view, R.id.takePhotoBtn, "field 'takePhotoBtn'", Button.class);
    view7f0901d4 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.takePaasbookPhoto();
      }
    });
    view = Utils.findRequiredView(source, R.id.takeAgainBtn, "method 'takeAgainPaasbookPhoto'");
    target.takeAgainBtn = Utils.castView(view, R.id.takeAgainBtn, "field 'takeAgainBtn'", Button.class);
    view7f0901ce = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.takeAgainPaasbookPhoto();
      }
    });
    view = Utils.findRequiredView(source, R.id.takAadhaarSelfiBtn, "method 'takeSelfiPhoto'");
    target.takAadhaarSelfiBtn = Utils.castView(view, R.id.takAadhaarSelfiBtn, "field 'takAadhaarSelfiBtn'", Button.class);
    view7f0901cd = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.takeSelfiPhoto();
      }
    });
    view = Utils.findRequiredView(source, R.id.takeAgainSelfiBtn, "method 'takeSelfiAgainPhoto'");
    target.takeAgainSelfiBtn = Utils.castView(view, R.id.takeAgainSelfiBtn, "field 'takeAgainSelfiBtn'", Button.class);
    view7f0901d0 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.takeSelfiAgainPhoto();
      }
    });
    target.upload_concentLL = Utils.findOptionalViewAsType(source, R.id.upload_concentLL, "field 'upload_concentLL'", LinearLayout.class);
    target.fileNameTV = Utils.findOptionalViewAsType(source, R.id.fileNameTV, "field 'fileNameTV'", ImageView.class);
    target.concent_textTV = Utils.findOptionalViewAsType(source, R.id.concent_textTV, "field 'concent_textTV'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AadharAccountActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.aadharScannerIV = null;
    target.passbookImageView = null;
    target.aadharImageView = null;
    target.enterAadharEt = null;
    target.enterAadhaeNameET = null;
    target.aadharGenderEt = null;
    target.aadharFatherNameEt = null;
    target.aadharYobEt = null;
    target.aadharAddressEt = null;
    target.aadharMobileNumberEt = null;
    target.aadharShgNameTv = null;
    target.aadharShgMemberNameTv = null;
    target.locationTv = null;
    target.aadhar_link_status = null;
    target.bank_account_link_status = null;
    target.aadharDetailLinearLayout = null;
    target.accountDetailLinearLayout = null;
    target.photoLinearLayout = null;
    target.aadharPhotoLinearLayout = null;
    target.accDetailCardView = null;
    target.aadharDetailCardView = null;
    target.aadharScanHeaderCV = null;
    target.bankDetailsHeaderCV = null;
    target.spinner_BankTypeSpin = null;
    target.spinner_BankNameSpin = null;
    target.spinner_BankBranchSpin = null;
    target.ifscTextView = null;
    target.accountNumberEt = null;
    target.accNumberTIL = null;
    target.confirmAccountNumberEt = null;
    target.submitAccountBtn = null;
    target.submitAadharBtn = null;
    target.takePhotoBtn = null;
    target.takeAgainBtn = null;
    target.takAadhaarSelfiBtn = null;
    target.takeAgainSelfiBtn = null;
    target.upload_concentLL = null;
    target.fileNameTV = null;
    target.concent_textTV = null;

    view7f090017.setOnClickListener(null);
    view7f090017 = null;
    view7f0901bd.setOnClickListener(null);
    view7f0901bd = null;
    view7f0901bc.setOnClickListener(null);
    view7f0901bc = null;
    view7f0901d4.setOnClickListener(null);
    view7f0901d4 = null;
    view7f0901ce.setOnClickListener(null);
    view7f0901ce = null;
    view7f0901cd.setOnClickListener(null);
    view7f0901cd = null;
    view7f0901d0.setOnClickListener(null);
    view7f0901d0 = null;
  }
}

// Generated code from Butter Knife. Do not modify!
package com.example.aadharscanner.activites;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.cardview.widget.CardView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.aadharscanner.R;
import com.google.android.material.textfield.TextInputEditText;
import java.lang.IllegalStateException;
import java.lang.Override;

public class KYC_ViewBinding implements Unbinder {
  private KYC target;

  private View view7f0900fa;

  private View view7f0901d6;

  private View view7f0901d2;

  private View view7f0901d5;

  private View view7f0901d1;

  private View view7f0901c0;

  @UiThread
  public KYC_ViewBinding(KYC target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public KYC_ViewBinding(final KYC target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.kyc_back_arrowIV, "method 'backArrow'");
    target.kyc_back_arrowIV = Utils.castView(view, R.id.kyc_back_arrowIV, "field 'kyc_back_arrowIV'", ImageView.class);
    view7f0900fa = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.backArrow();
      }
    });
    target.memberDetailsTV = Utils.findOptionalViewAsType(source, R.id.memberDetailsTV, "field 'memberDetailsTV'", TextView.class);
    target.kycDockTypeMSB = Utils.findOptionalViewAsType(source, R.id.kyc_doc_typeMBS, "field 'kycDockTypeMSB'", Spinner.class);
    target.document_idTIET = Utils.findOptionalViewAsType(source, R.id.document_idTIET, "field 'document_idTIET'", TextInputEditText.class);
    target.frontdocCV = Utils.findOptionalViewAsType(source, R.id.frontdocCV, "field 'frontdocCV'", CardView.class);
    target.front_image_titleTV = Utils.findOptionalViewAsType(source, R.id.front_image_titleTV, "field 'front_image_titleTV'", TextView.class);
    target.frontImageView = Utils.findOptionalViewAsType(source, R.id.frontImageView, "field 'frontImageView'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.takefronimagetBTN, "method 'takeFrontImage'");
    target.takefronimagetBTN = Utils.castView(view, R.id.takefronimagetBTN, "field 'takefronimagetBTN'", Button.class);
    view7f0901d6 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.takeFrontImage();
      }
    });
    view = Utils.findRequiredView(source, R.id.takeAgainfrontBTN, "method 'takeAgainFrontImage'");
    target.takeAgainfrontBTN = Utils.castView(view, R.id.takeAgainfrontBTN, "field 'takeAgainfrontBTN'", Button.class);
    view7f0901d2 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.takeAgainFrontImage();
      }
    });
    target.backdocCV = Utils.findOptionalViewAsType(source, R.id.backdocCV, "field 'backdocCV'", CardView.class);
    target.back_image_titleTV = Utils.findOptionalViewAsType(source, R.id.back_image_titleTV, "field 'back_image_titleTV'", TextView.class);
    view = Utils.findRequiredView(source, R.id.takebackImageBTN, "method 'takeBackImage'");
    target.takebackImageBTN = Utils.castView(view, R.id.takebackImageBTN, "field 'takebackImageBTN'", Button.class);
    view7f0901d5 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.takeBackImage();
      }
    });
    target.backImageView = Utils.findOptionalViewAsType(source, R.id.backImageView, "field 'backImageView'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.takeAgainbackImageBTN, "method 'takeAgainBackImage'");
    target.takeAgainbackImageBTN = Utils.castView(view, R.id.takeAgainbackImageBTN, "field 'takeAgainbackImageBTN'", Button.class);
    view7f0901d1 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.takeAgainBackImage();
      }
    });
    view = Utils.findRequiredView(source, R.id.submitdetailsBTN, "method 'submitDetails'");
    target.submitdetailsBTN = Utils.castView(view, R.id.submitdetailsBTN, "field 'submitdetailsBTN'", Button.class);
    view7f0901c0 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.submitDetails();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    KYC target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.kyc_back_arrowIV = null;
    target.memberDetailsTV = null;
    target.kycDockTypeMSB = null;
    target.document_idTIET = null;
    target.frontdocCV = null;
    target.front_image_titleTV = null;
    target.frontImageView = null;
    target.takefronimagetBTN = null;
    target.takeAgainfrontBTN = null;
    target.backdocCV = null;
    target.back_image_titleTV = null;
    target.takebackImageBTN = null;
    target.backImageView = null;
    target.takeAgainbackImageBTN = null;
    target.submitdetailsBTN = null;

    view7f0900fa.setOnClickListener(null);
    view7f0900fa = null;
    view7f0901d6.setOnClickListener(null);
    view7f0901d6 = null;
    view7f0901d2.setOnClickListener(null);
    view7f0901d2 = null;
    view7f0901d5.setOnClickListener(null);
    view7f0901d5 = null;
    view7f0901d1.setOnClickListener(null);
    view7f0901d1 = null;
    view7f0901c0.setOnClickListener(null);
    view7f0901c0 = null;
  }
}

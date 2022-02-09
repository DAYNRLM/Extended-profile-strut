// Generated code from Butter Knife. Do not modify!
package com.example.aadharscanner.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.aadharscanner.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ShgMemberListAdapter$MyViewHolder_ViewBinding implements Unbinder {
  private ShgMemberListAdapter.MyViewHolder target;

  @UiThread
  public ShgMemberListAdapter$MyViewHolder_ViewBinding(ShgMemberListAdapter.MyViewHolder target,
      View source) {
    this.target = target;

    target.shgMemberNameTV = Utils.findOptionalViewAsType(source, R.id.shgMemberNameTV, "field 'shgMemberNameTV'", TextView.class);
    target.member_statusTV = Utils.findOptionalViewAsType(source, R.id.member_statusTV, "field 'member_statusTV'", TextView.class);
    target.change_statusMBS = Utils.findOptionalViewAsType(source, R.id.change_statusMBS, "field 'change_statusMBS'", MaterialBetterSpinner.class);
    target.kyc_BTN = Utils.findOptionalViewAsType(source, R.id.kyc_BTN, "field 'kyc_BTN'", TextView.class);
    target.kycnotupdatedIV = Utils.findOptionalViewAsType(source, R.id.kycnotupdatedIV, "field 'kycnotupdatedIV'", ImageView.class);
    target.kycupdatedIV = Utils.findOptionalViewAsType(source, R.id.kycupdatedIV, "field 'kycupdatedIV'", ImageView.class);
    target.aadharPendingTV = Utils.findOptionalViewAsType(source, R.id.aadharPendingTV, "field 'aadharPendingTV'", TextView.class);
    target.bankAccPendingTV = Utils.findOptionalViewAsType(source, R.id.bankAccPendingTV, "field 'bankAccPendingTV'", TextView.class);
    target.designationTV = Utils.findOptionalViewAsType(source, R.id.designationTV, "field 'designationTV'", TextView.class);
    target.designationCurrentStatusTV = Utils.findOptionalViewAsType(source, R.id.designation_current_statusTV, "field 'designationCurrentStatusTV'", TextView.class);
    target.changeDesignationRL = Utils.findOptionalViewAsType(source, R.id.change_designationRL, "field 'changeDesignationRL'", RelativeLayout.class);
    target.changeDesignationTV = Utils.findOptionalViewAsType(source, R.id.change_designationTV, "field 'changeDesignationTV'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ShgMemberListAdapter.MyViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.shgMemberNameTV = null;
    target.member_statusTV = null;
    target.change_statusMBS = null;
    target.kyc_BTN = null;
    target.kycnotupdatedIV = null;
    target.kycupdatedIV = null;
    target.aadharPendingTV = null;
    target.bankAccPendingTV = null;
    target.designationTV = null;
    target.designationCurrentStatusTV = null;
    target.changeDesignationRL = null;
    target.changeDesignationTV = null;
  }
}

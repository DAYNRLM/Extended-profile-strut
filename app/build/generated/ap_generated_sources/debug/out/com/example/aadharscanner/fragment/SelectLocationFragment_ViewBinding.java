// Generated code from Butter Knife. Do not modify!
package com.example.aadharscanner.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.aadharscanner.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SelectLocationFragment_ViewBinding implements Unbinder {
  private SelectLocationFragment target;

  private View view7f0900e1;

  @UiThread
  public SelectLocationFragment_ViewBinding(final SelectLocationFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.goToNextBtn, "method 'getDateFragment'");
    target.goToShgList = Utils.castView(view, R.id.goToNextBtn, "field 'goToShgList'", TextView.class);
    view7f0900e1 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.getDateFragment();
      }
    });
    target.blockNameTv = Utils.findOptionalViewAsType(source, R.id.blockNameTv, "field 'blockNameTv'", TextView.class);
    target.totalShgTv = Utils.findOptionalViewAsType(source, R.id.totalShgTv, "field 'totalShgTv'", TextView.class);
    target.totalShgMemberTv = Utils.findOptionalViewAsType(source, R.id.totalShgMemberTv, "field 'totalShgMemberTv'", TextView.class);
    target.aadharLinkedTv = Utils.findOptionalViewAsType(source, R.id.aadharLinkedTv, "field 'aadharLinkedTv'", TextView.class);
    target.aadharPendingTv = Utils.findOptionalViewAsType(source, R.id.aadharPendingTv, "field 'aadharPendingTv'", TextView.class);
    target.totalAccountLinkedTv = Utils.findOptionalViewAsType(source, R.id.totalAccountLinked, "field 'totalAccountLinkedTv'", TextView.class);
    target.spinner_selectGP = Utils.findOptionalViewAsType(source, R.id.spinner_selectGP, "field 'spinner_selectGP'", MaterialBetterSpinner.class);
    target.spinner_selectVillage = Utils.findOptionalViewAsType(source, R.id.spinner_selectVillage, "field 'spinner_selectVillage'", MaterialBetterSpinner.class);
    target.detailsLL = Utils.findOptionalViewAsType(source, R.id.detailsLL, "field 'detailsLL'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SelectLocationFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.goToShgList = null;
    target.blockNameTv = null;
    target.totalShgTv = null;
    target.totalShgMemberTv = null;
    target.aadharLinkedTv = null;
    target.aadharPendingTv = null;
    target.totalAccountLinkedTv = null;
    target.spinner_selectGP = null;
    target.spinner_selectVillage = null;
    target.detailsLL = null;

    view7f0900e1.setOnClickListener(null);
    view7f0900e1 = null;
  }
}

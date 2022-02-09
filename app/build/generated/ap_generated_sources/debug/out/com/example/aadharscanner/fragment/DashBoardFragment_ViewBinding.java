// Generated code from Butter Knife. Do not modify!
package com.example.aadharscanner.fragment;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.aadharscanner.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DashBoardFragment_ViewBinding implements Unbinder {
  private DashBoardFragment target;

  private View view7f090190;

  private View view7f0901c1;

  @UiThread
  public DashBoardFragment_ViewBinding(final DashBoardFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.selectGpListBtn, "method 'goToListOfSHG'");
    target.selectGpListBtn = Utils.castView(view, R.id.selectGpListBtn, "field 'selectGpListBtn'", TextView.class);
    view7f090190 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.goToListOfSHG();
      }
    });
    target.unsynced_aadhar = Utils.findOptionalViewAsType(source, R.id.unsynced_aadhar, "field 'unsynced_aadhar'", TextView.class);
    target.unsynced_bank = Utils.findOptionalViewAsType(source, R.id.unsynced_bank, "field 'unsynced_bank'", TextView.class);
    target.unsynced_add_member = Utils.findOptionalViewAsType(source, R.id.unsynced_add_member, "field 'unsynced_add_member'", TextView.class);
    target.unsynced_kyc_doc = Utils.findOptionalViewAsType(source, R.id.unsynced_kyc_doc, "field 'unsynced_kyc_doc'", TextView.class);
    target.unsynced_modified_member = Utils.findOptionalViewAsType(source, R.id.unsynced_modified_member, "field 'unsynced_modified_member'", TextView.class);
    target.unsynced_modified_shg = Utils.findOptionalViewAsType(source, R.id.unsynced_modified_shg, "field 'unsynced_modified_shg'", TextView.class);
    view = Utils.findRequiredView(source, R.id.sync_dataTV, "method 'syncData'");
    target.sync_dataTV = Utils.castView(view, R.id.sync_dataTV, "field 'sync_dataTV'", TextView.class);
    view7f0901c1 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.syncData();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    DashBoardFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.selectGpListBtn = null;
    target.unsynced_aadhar = null;
    target.unsynced_bank = null;
    target.unsynced_add_member = null;
    target.unsynced_kyc_doc = null;
    target.unsynced_modified_member = null;
    target.unsynced_modified_shg = null;
    target.sync_dataTV = null;

    view7f090190.setOnClickListener(null);
    view7f090190 = null;
    view7f0901c1.setOnClickListener(null);
    view7f0901c1 = null;
  }
}

// Generated code from Butter Knife. Do not modify!
package com.example.aadharscanner.activites;

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

public class DeleteVillage_ViewBinding implements Unbinder {
  private DeleteVillage target;

  private View view7f0901c2;

  @UiThread
  public DeleteVillage_ViewBinding(DeleteVillage target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public DeleteVillage_ViewBinding(final DeleteVillage target, View source) {
    this.target = target;

    View view;
    target.unsynced_aadhar = Utils.findOptionalViewAsType(source, R.id.unsynced_aadharV, "field 'unsynced_aadhar'", TextView.class);
    target.unsynced_bank = Utils.findOptionalViewAsType(source, R.id.unsynced_bankV, "field 'unsynced_bank'", TextView.class);
    target.unsynced_add_member = Utils.findOptionalViewAsType(source, R.id.unsynced_add_memberV, "field 'unsynced_add_member'", TextView.class);
    target.unsynced_kyc_doc = Utils.findOptionalViewAsType(source, R.id.unsynced_kyc_doc, "field 'unsynced_kyc_doc'", TextView.class);
    target.unsynced_modified_shg = Utils.findOptionalViewAsType(source, R.id.unsynced_modified_shg, "field 'unsynced_modified_shg'", TextView.class);
    target.unsynced_modified_member = Utils.findOptionalViewAsType(source, R.id.unsynced_modified_memberV, "field 'unsynced_modified_member'", TextView.class);
    view = Utils.findRequiredView(source, R.id.sync_dataTVV, "method 'syncData'");
    target.sync_dataTV = Utils.castView(view, R.id.sync_dataTVV, "field 'sync_dataTV'", TextView.class);
    view7f0901c2 = view;
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
    DeleteVillage target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.unsynced_aadhar = null;
    target.unsynced_bank = null;
    target.unsynced_add_member = null;
    target.unsynced_kyc_doc = null;
    target.unsynced_modified_shg = null;
    target.unsynced_modified_member = null;
    target.sync_dataTV = null;

    view7f0901c2.setOnClickListener(null);
    view7f0901c2 = null;
  }
}

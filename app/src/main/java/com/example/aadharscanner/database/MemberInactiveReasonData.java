package com.example.aadharscanner.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class MemberInactiveReasonData {

 private String reasonId;
 private String reason;
	@Generated(hash = 802729175)
	public MemberInactiveReasonData(String reasonId, String reason) {
		this.reasonId = reasonId;
		this.reason = reason;
	}
	@Generated(hash = 120362663)
	public MemberInactiveReasonData() {
	}
	public String getReasonId() {
		return this.reasonId;
	}
	public void setReasonId(String reasonId) {
		this.reasonId = reasonId;
	}
	public String getReason() {
		return this.reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}


}

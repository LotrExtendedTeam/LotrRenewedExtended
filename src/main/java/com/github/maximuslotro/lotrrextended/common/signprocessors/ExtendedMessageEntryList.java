package com.github.maximuslotro.lotrrextended.common.signprocessors;

public class ExtendedMessageEntryList extends ExtendedMessageEntryBasic{
	private String lines0;
	private String lines1;
	private String lines2;
	private String lines3;

	public ExtendedMessageEntryList(String pLine0, String pLine1, String pLine2, String pLine3) {
		this.lines0 = pLine0;
		this.lines1 = pLine1;
		this.lines2 = pLine2;
		this.lines3 = pLine3;
	}

	public ExtendedMessageEntryList(String pLines[]) {
		this.lines0 = pLines[0];
		this.lines1 = pLines[1];
		this.lines2 = pLines[2];
		this.lines3 = pLines[3];
	}

	@Override
	public String getLine0() {
		return lines0;
	}

	@Override
	public String getLine1() {
		return lines1;
	}

	@Override
	public String getLine2() {
		return lines2;
	}

	@Override
	public String getLine3() {
		return lines3;
	}

}

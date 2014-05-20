package sk.uniza.fri.II008.s3.simulation;

import OSPABA.IdList;

public class MessageType extends IdList
{
	public static final int INIT = 2;
	public static final int IMPORT = 3;
	public static final int EXPORT = 5;
	public static final int STORE_ROLL = 7;
	public static final int STORE_ROLL_DONE = 14;
	public static final int PROCESS_ROLL = 11;
	public static final int PROCESS_ROLL_DONE = 22;
	public static final int TRANSFER_CRANE = 13;
	public static final int TRANSFER_CRANE_DONE = 26;
	public static final int TRANSPORT_ROLL = 17;
	public static final int TRANSPORT_ROLL_DONE = 34;
	public static final int ASSIGN_VEHICLE = 19;
	public static final int ASSIGN_VEHICLE_DONE = 38;
	public static final int TRANSFER_VEHICLE = 23;
	public static final int TRANSFER_VEHICLE_DONE = 46;
	public static final int RELEASE_VEHICLE = 29;
	public static final int PREPARE_ROLL_TO_EXPORT = 31;
	public static final int PREPARE_ROLL_TO_EXPORT_DONE = 62;
	public static final int EMPTY_STORAGE = 37;
	public static final int TRANSFER_EMPLOYEE = 41;
	public static final int TRANSFER_EMPLOYEE_DONE = 82;
}

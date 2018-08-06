package au.com.iwsoftware.sqlDbcon.delete_old_data;

import au.com.iwsoftware.sqlDbcon.TableOperator;

public class DeleteOldDataTableOperator extends TableOperator {
	public static final String NAME = "DeleteOldData";

	public DeleteOldDataTableOperator(String schemaName) {
		super(schemaName, NAME);
	}

}

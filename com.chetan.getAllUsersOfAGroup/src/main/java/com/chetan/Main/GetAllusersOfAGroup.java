package com.chetan.Main;

import org.apache.log4j.Logger;

import com.appiancorp.services.ServiceContext;
import com.appiancorp.services.ServiceContextFactory;
import com.appiancorp.suiteapi.common.Name;
import com.appiancorp.suiteapi.common.ServiceLocator;
import com.appiancorp.suiteapi.common.exceptions.InvalidGroupException;
import com.appiancorp.suiteapi.common.exceptions.PrivilegeException;
import com.appiancorp.suiteapi.expression.ExpressionEvaluationService;
import com.appiancorp.suiteapi.expression.ExpressionService;
import com.appiancorp.suiteapi.personalization.GroupDataType;
import com.appiancorp.suiteapi.personalization.GroupService;
import com.appiancorp.suiteapi.process.exceptions.SmartServiceException;
import com.appiancorp.suiteapi.process.framework.AppianSmartService;
import com.appiancorp.suiteapi.process.framework.Input;
import com.appiancorp.suiteapi.process.framework.MessageContainer;
import com.appiancorp.suiteapi.process.framework.Required;
import com.appiancorp.suiteapi.process.framework.SmartServiceContext;

import com.appiancorp.suiteapi.process.palette.PaletteInfo; 

@PaletteInfo(paletteCategory = "Integration Services", palette = "Connectivity Services") 
public class GetAllusersOfAGroup extends AppianSmartService {

	private static final Logger LOG = Logger
			.getLogger(GetAllusersOfAGroup.class);
	
	private final SmartServiceContext smartServiceCtx;
	private Long groupToGetUsers;
	private String[] userNames;

	@Override
	public void run() throws SmartServiceException {
		// TODO Auto-generated method stub

	}

	public GetAllusersOfAGroup(SmartServiceContext smartServiceCtx) {
		super();
		this.smartServiceCtx = smartServiceCtx;
	}

	public void onSave(MessageContainer messages) {
	}

	public void validate(MessageContainer messages) {
	}

	@Input(required = Required.OPTIONAL)
	@Name("groupToGetUsers")
	@GroupDataType
	public void setGroupToGetUsers(Long val) {
		this.groupToGetUsers = val;
	}

	@Name("userNames")
	public String[] getUserNames() {
		ServiceContext sc = ServiceLocator.getAdministratorServiceContext();
		
		GroupService gs = ServiceLocator.getGroupService(sc) ;
		String [] userNames = null;
		
		
		try {
			userNames = gs.getMemberUsernames(groupToGetUsers) ;
			LOG.info("Got the userNames");
		} catch (InvalidGroupException e) {
			LOG.error("Invalid GRoup Exception");
			e.printStackTrace();
		} 
		return userNames;
		
		
	}

}

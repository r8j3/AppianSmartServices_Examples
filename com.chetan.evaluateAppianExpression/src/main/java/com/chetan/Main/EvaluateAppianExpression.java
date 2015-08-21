package com.chetan.Main;

import org.apache.log4j.Logger;

import com.appiancorp.process.expression.ExpressionEvaluationException;
import com.appiancorp.services.ServiceContext;
import com.appiancorp.suiteapi.common.Name;
import com.appiancorp.suiteapi.common.ServiceLocator;
import com.appiancorp.suiteapi.process.ProcessDesignService;
import com.appiancorp.suiteapi.process.exceptions.SmartServiceException;
import com.appiancorp.suiteapi.process.framework.AppianSmartService;
import com.appiancorp.suiteapi.process.framework.Input;
import com.appiancorp.suiteapi.process.framework.MessageContainer;
import com.appiancorp.suiteapi.process.framework.Required;
import com.appiancorp.suiteapi.process.framework.SmartServiceContext;
import com.appiancorp.suiteapi.process.palette.PaletteInfo;
import com.appiancorp.suiteapi.type.NamedTypedValue;
import com.appiancorp.suiteapi.type.TypedValue;
import com.appiancorp.type.AppianTypeLong;

/*
 * This is an example of how to call Appian expression rules from within Java smart 
 * service code.
 */
@PaletteInfo(paletteCategory = "Integration Services", palette = "Connectivity Services") 
public class EvaluateAppianExpression extends AppianSmartService {

	private static final Logger LOG = Logger
			.getLogger(EvaluateAppianExpression.class);
	private final SmartServiceContext smartServiceCtx;
	private String inputStr;
	private String outputStr;

	@Override
	public void run() throws SmartServiceException {
		// TODO Auto-generated method stub

	}

	public EvaluateAppianExpression(SmartServiceContext smartServiceCtx) {
		super();
		this.smartServiceCtx = smartServiceCtx;
	}

	public void onSave(MessageContainer messages) {
	}

	public void validate(MessageContainer messages) {
	}

	@Input(required = Required.OPTIONAL)
	@Name("inputStr")
	public void setInputStr(String val) {
		this.inputStr = val;
	}

	@Name("outputStr")
	public String getOutputStr() {
		
		ServiceContext sc = ServiceLocator.getAdministratorServiceContext() ;
		ProcessDesignService pds = ServiceLocator.getProcessDesignService(sc) ;
		String expressionRuleName = "=TEST_FROM_SMART_SERVICE(txt_name)" ;
		
		// we are creating a NamedTypedValue with the variable name "txt_name"
		//data type -String
		//and value: "Chetan"
		/*
		 * Notice how we are using a evaluateExpression function call.
		 * and then passing the parameters to it using an array of NamedTypedValue. 
		 */
		NamedTypedValue ntv = new NamedTypedValue("txt_name", AppianTypeLong.STRING, "Chetan");
		TypedValue result = new TypedValue(AppianTypeLong.STRING);
		NamedTypedValue ruleParams[] = {ntv};
		
		try {
			result = pds.evaluateExpression(expressionRuleName, ruleParams);
			LOG.info("Called the expression rule");
		} catch (ExpressionEvaluationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result.getValue().toString();
	}

}

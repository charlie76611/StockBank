package com.charlie.transaction.web;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.charlie.resource.AppConstants;
import com.charlie.resource.commons.BaseActionBean;
import com.charlie.resource.commons.ConditionModel;
import com.charlie.resource.commons.JqGridData;
import com.charlie.resource.commons.QueryConditionModel;
import com.charlie.resource.enums.ActionType;
import com.charlie.transaction.model.RandomDinnerModel;
import com.charlie.transaction.service.RandomDinnerService;

@UrlBinding("/trans/rddinner.action")
public class RandomDinnerActionBean extends BaseActionBean<RandomDinnerModel> {
	
	private static final String KEY_ID = RandomDinnerActionBean.class.getName() + ".id";
	private static final String KEY_FORM_OBJ = RandomDinnerActionBean.class.getName() + ".formObj";
	private static final String KEY_QUERY_OBJ = RandomDinnerActionBean.class.getName() + ".queryObj";

	private boolean click;	
	private String[] category;
	
	@SpringBean
	private RandomDinnerService service;
	
	public RandomDinnerActionBean() {
		if (model == null) {
			model = new RandomDinnerModel();
		}
		if (form == null) {
			form = new RandomDinnerModel();
		}
		if (qcModel == null) {
			qcModel = new QueryConditionModel<RandomDinnerModel>();
		}
	}
	
	@DefaultHandler
	public Resolution list() {
		
		try {
			// condition handle
			if(form.getFoodType() != null) {
				qcModel.addCondition(new ConditionModel(ConditionModel.ConditionPrefix.AND, "food_type", ConditionModel.ConditionType.EQ,"'"+form.getFoodType()+"'"));
			}
			if(form.getStoreType() != null) {
				qcModel.addCondition(new ConditionModel(ConditionModel.ConditionPrefix.AND, "store_type", ConditionModel.ConditionType.EQ,"'"+form.getStoreType()+"'"));
			}
			qcModel = service.getByQueryCondition(qcModel);
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return new ForwardResolution("/trans/dinner/list.tiles");
	}
	
//	@RequestMapping("/trans/rddinner/list")
//	public JqGridData<RandomDinnerModel> getList(QueryConditionModel<RandomDinnerModel> qcModel) {
//		return new JqGridData<RandomDinnerModel>(records, rows);
//		
//	}
	
	/**
	 * 新增頁
	 * @return
	 */
	public Resolution add() {
		try {
			Object formObj = getContext().getSessionAttribute(KEY_FORM_OBJ);
			form = formObj == null ? new RandomDinnerModel() : (RandomDinnerModel) formObj; 
			actionType = ActionType.INSERT;
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return new ForwardResolution("/trans/dinner/add.tiles");
	}
	
	/**
	 * 儲存
	 * @return
	 */
	public Resolution insert() {
		try {
			String cat = "";			
			for (String s : category) {
				cat += s + ",";
			}
			form.setCategory(StringUtils.substring(cat, 0, cat.length() - 1));
			
			super.copyProperties(form, model);
			debugModel();
			service.insert(model, getLoginAccount());
			getContext().removeSessionAttribute(KEY_FORM_OBJ);
			
			this.getContext().setInfoMsg(AppConstants.INSERT_SUCCESS);
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			getContext().putSessionAttribute(KEY_FORM_OBJ, model); // 保留這次輸入之資料, 當作重來新增之依據
		}
		saveMessages();
		return new RedirectResolution(getClass(),"add").flash(this);
	}
	
	/**
	 * 編輯頁
	 * @return
	 */
	public Resolution edit() {
		try {
			actionType = ActionType.UPDATE;
			if (sysid == 0) { // 若parameter沒有ID, 再檢查attribute中有無, 因為執行update之後, 也是回到修改頁
				sysid = (Long) this.getContext().getRequestAttribute(KEY_ID);
			}
			model = service.getById(sysid);
			super.copyProperties(model, form);
			debugModel();
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return new ForwardResolution("/trans/dinner/edit.tiles");
	}
	
	/**
	 * 更新
	 * @return
	 */
	public Resolution update() {
		try {
			String cat = "";			
			for (String s : category) {
				cat += s + ",";
			}
			form.setCategory(StringUtils.substring(cat, 0, cat.length() - 1));
			//form.setCloseTime(DateUtil.nowTimestamp());
			debugModel();
			
			super.copyProperties(form, model);
			model = service.update(model, getLoginAccount());
			
			this.getContext().setInfoMsg(AppConstants.UPDATE_SUCCESS);
			
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		this.getContext().putRequestAttribute(KEY_ID, model.getSysid());
		saveMessages();
		return this.edit();
	}
	
	/**
	 * 刪除
	 * @return
	 */
	public Resolution delete() {
		try {
			service.delete(sysid, getLoginAccount());
			this.getContext().setErrorMsg(AppConstants.DELETE_SUCCESS);
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		saveMessages();
		return this.list();
	}
	
	/**
	 * 隨機取得晚餐
	 * @return
	 */
	public Resolution getRandomDinner() {
		
		try {
			QueryConditionModel<RandomDinnerModel> qcModel = new QueryConditionModel<RandomDinnerModel>();
			// condition handle
			if(form.getFoodType() != null) {
				qcModel.addCondition(new ConditionModel(ConditionModel.ConditionPrefix.AND, "food_type", ConditionModel.ConditionType.EQ,"'"+form.getFoodType()+"'"));
			}
			if(form.getStoreType() != null) {
				qcModel.addCondition(new ConditionModel(ConditionModel.ConditionPrefix.AND, "store_type", ConditionModel.ConditionType.EQ,"'"+form.getStoreType()+"'"));
			}
			if(form.getRegion() != null) {
				qcModel.addCondition(new ConditionModel(ConditionModel.ConditionPrefix.AND, "region", ConditionModel.ConditionType.EQ,"'"+form.getRegion()+"'"));
			}
			
			qcModel.setPageno(QueryConditionModel.PAGENO_QUERY_ALL);
			model = service.getRandomDinner(qcModel);
			debugModel();
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return new ForwardResolution("/trans/dinner/list.tiles");
	}

	public boolean isClick() {
		return click;
	}

	public void setClick(boolean click) {
		this.click = click;
	}

	public String[] getCategory() {
		return category;
	}

	public void setCategory(String[] category) {
		this.category = category;
	}

}

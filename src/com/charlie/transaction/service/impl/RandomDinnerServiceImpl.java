package com.charlie.transaction.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.charlie.resource.commons.QueryConditionModel;
import com.charlie.transaction.dao.RandomDinnerDao;
import com.charlie.transaction.model.RandomDinnerModel;
import com.charlie.transaction.service.RandomDinnerService;

@Service
public class RandomDinnerServiceImpl implements RandomDinnerService {

	protected Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private RandomDinnerDao dao;
	
	@Override
	public RandomDinnerModel getRandomDinner(QueryConditionModel<RandomDinnerModel> qcModel) throws Exception {
		// get all dinners
		List<RandomDinnerModel> models = this.getByQueryCondition(qcModel).getModels();
		if (models.isEmpty()) return null;
		
		RandomDinnerModel[] modelAry = new RandomDinnerModel[models.size()];
		int index = 0;
		for (RandomDinnerModel model : models) {
			modelAry[index++] = model;
		}
		// random index
		Integer rd = getRandomNumber(); 	
		RandomDinnerModel model = modelAry[rd % modelAry.length];
		
		// 更新隨機次數
		dao.updateRandomTimes(String.valueOf(model.getSysid()), model.getRandomtimes() + 1);
		return model;
	}
	
	@Override
	public QueryConditionModel<RandomDinnerModel> getAll() throws Exception {
		QueryConditionModel<RandomDinnerModel> qcModel = new QueryConditionModel<RandomDinnerModel>();
		List<RandomDinnerModel> models = dao.findAll();
		
		qcModel.setModels(models);
		qcModel.setTotalCount(models.size());
		int totalPage = new BigDecimal(qcModel.getTotalCount()).divide(new BigDecimal(qcModel.getPagesize()), 0, RoundingMode.UP).intValue();
		qcModel.setTotalPage(totalPage);
		return qcModel;
	}

	@Override
	public QueryConditionModel<RandomDinnerModel> getByQueryCondition(QueryConditionModel<RandomDinnerModel> qcModel) throws Exception {
		
		List<RandomDinnerModel> models = dao.findByQueryCondition(qcModel);
		for (RandomDinnerModel model : models) {
			if (StringUtils.isNotBlank(model.getCategory())) {
				String category = model.getCategory();
				category = category.replace("BK", "早餐").replace("LH", "午餐").replace("DN", "晚餐").replace("NS", "宵夜");
				model.setCategory(category);
			}
		}
		
		qcModel.setModels(models);
		qcModel.setTotalCount(dao.findTotalCount(qcModel));
		int totalPage = new BigDecimal(qcModel.getTotalCount()).divide(new BigDecimal(qcModel.getPagesize()), 0, RoundingMode.UP).intValue();
		qcModel.setTotalPage(totalPage);
		return qcModel;
	}

	@Override
	public RandomDinnerModel getById(Long sysid) {
		return dao.findById(sysid);
	}

	@Override
	public RandomDinnerModel insert(RandomDinnerModel model, String account) throws Exception {
		return dao.insert(model, account);
	}

	@Override
	public RandomDinnerModel update(RandomDinnerModel model, String account) throws Exception {
		if (dao.update(model, account) > 0) {
			return model;
		}
		return null;
	}

	@Override
	public void delete(RandomDinnerModel model, String account) throws Exception {
		this.delete(model.getSysid(), account);
	}

	@Override
	public void delete(Long sysid, String account) throws Exception {
		dao.delete(sysid, account);
	}

	@Override
	public void delete(Long[] sysids, String account) throws Exception {
		for (Long sysid : sysids) {
			this.delete(sysid, account);
		}
	}
	
	private Integer getRandomNumber() {
		return new BigDecimal(Math.random()).multiply(BigDecimal.TEN).multiply(BigDecimal.TEN).divide(BigDecimal.ONE, RoundingMode.HALF_UP).intValue();
	}
}

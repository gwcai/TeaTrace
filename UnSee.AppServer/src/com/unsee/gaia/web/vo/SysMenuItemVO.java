package com.unsee.gaia.web.vo;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.unsee.gaia.biz.entities.SysMenusEntity;
import com.unsee.gaia.biz.services.SysMenusService;

public class SysMenuItemVO {
	private final static Logger logger = Logger.getLogger(SysMenuItemVO.class);
	
	public static List<SysMenuItemVO> getMenusByUserId(int userId, String[] systemNames) {
		List<SysMenusEntity> menus = SysMenusService.getInstance()
				.getMenusByUserId(userId);
		List<SysMenuItemVO> menuItems = new ArrayList<SysMenuItemVO>();
		buildMenuVO(menus, -1, menuItems, java.util.Arrays.asList(systemNames));
		
		return menuItems;
	}

	private static void buildMenuVO(List<SysMenusEntity> menus,
			int pid, List<SysMenuItemVO> menuVOs, List<String> systemNames) {
		for(SysMenusEntity menu : menus) {
			if(pid == menu.getPid() && systemNames.indexOf(menu.getBelongs()) !=-1) {
				SysMenuItemVO vo = new SysMenuItemVO();
				
				vo.setId(menu.getId());
				vo.setTitle(menu.getTitle());
				vo.setTips(menu.getTips());
				vo.setFeatureCode(menu.getFeatureCode());
				vo.setEnabled(menu.getFeature() != null && menu.getFeature().isEnabled());
				vo.setExpression(menu.getFeature() != null?menu.getFeature().getExpression():null);
				menuVOs.add(vo);
				
				buildMenuVO(menus, Integer.valueOf(menu.getId()), vo.children, systemNames);
			} else {
				logger.warn("忽略不加载的菜单"+ menu.getTitle());
			}
		}
	}

	private String id;
	private String title;
	private String tips;
	private String featureCode;
	private boolean enabled;
	private String expression;
	private List<SysMenuItemVO> children = new ArrayList<SysMenuItemVO>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<SysMenuItemVO> getChildren() {
		return children;
	}

	public void setChildren(List<SysMenuItemVO> children) {
		this.children = children;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public String getFeatureCode() {
		return featureCode;
	}

	public void setFeatureCode(String featureCode) {
		this.featureCode = featureCode;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}
}

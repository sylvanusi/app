package com.more.app.authorization;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.more.app.entity.AppResource;
import com.more.app.entity.AppRole;
import com.more.app.entity.User;
import com.more.app.entity.enums.ModuleName;
import com.more.app.repository.AppResourceRepository;
import com.more.app.repository.AppRoleRepository;
import com.more.app.repository.UserRepository;
import com.vaadin.flow.server.VaadinSession;

public class PermissionController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AppResourceRepository appResourceRepository;
	
	@Autowired
	private AppRoleRepository appRoleRepository;

	public AppRole getRole() {
		String currentUser = (String) VaadinSession.getCurrent().getAttribute("currentUser");
		User user = userRepository.findByUsername(currentUser);
		AppRole role = appRoleRepository.findById(user.getRoleId()).get();
		return role;
	}

	public AppRole getRole(String username) {
		String currentUser = (String) VaadinSession.getCurrent().getAttribute("currentUser");
		User user = userRepository.findByUsername(currentUser);
		String query = "SELECT p FROM AppRole p WHERE p.id = :id";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", user.getRoleId());
		AppRole role = null;
				//(AppRole) FacadeFactory.getFacade().find(query, parameters);
		return role;
	}

	public User getUser() {
		String currentUser = (String) VaadinSession.getCurrent().getAttribute("currentUser");
		User user = userRepository.findByUsername(currentUser);
		return user;
	}

	public static boolean getModule(String role, ModuleName module) {
		boolean retval = false;
		String query = "SELECT p FROM PermissionEntity p WHERE p.role = :role";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("role", role);
		List<PermissionEntity> permEntity = null;
				//FacadeFactory.getFacade().list(query, parameters);
		for (PermissionEntity perm : permEntity) {
			long id = Long.parseLong(perm.getResource());
			PermissionType type = perm.getType();
			if (type.equals(PermissionType.ALLOW)) {
				AppResource res = null;
						//FacadeFactory.getFacade().find(AppResource.class, Long.valueOf(id));
				if (res != null && res.getModuleName().equals(module)) {
					retval = true;
					break;
				}
			}
		}
		return retval;
	}

	public boolean hasAccess(String action, Long resourceId) {
		String currentUser = (String) VaadinSession.getCurrent().getAttribute("currentUser");
		User user = userRepository.findByUsername(currentUser);
		if (user.getRoleId() != null) {
			String whereClause = " p.role = :role AND p.resource = :resource AND ("
					+ "(p.action = :action AND p.type = :typeAllow) OR " + "(p.type = :typeAllowAll))";
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("role", user.getRoleId().toString());
			parameters.put("resource", resourceId.toString());
			parameters.put("action", action);
			parameters.put("typeAllow", PermissionType.ALLOW);
			parameters.put("typeAllowAll", PermissionType.ALLOW_ALL);

			Long count = 0L;
					//FacadeFactory.getFacade().count(PermissionEntity.class, whereClause, parameters);

			return count > 0 ? true : false;
		}
		return false;

	

	}
}

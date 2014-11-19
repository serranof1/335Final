package model;

import resources.Resource;

public abstract class Items {
	private Resource resource;
	
	public Items(Resource source) {
		resource = source;
	}
}

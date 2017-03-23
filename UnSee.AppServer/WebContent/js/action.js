function ActionManager() {
	this.actions = [];
	return this;
}

ActionManager.prototype.registerAction=function(options) {
	this.actions.push({key:options.key, cb:options.cb});
}

ActionManager.prototype.unregisterAction=function(key) {
	var readyToRemove = [];
	$(this.actions).each(function(){
		if(this.key == key)
			readyToRemove.push(this);
	});
	$(readyToRemove).each(function(){
		this.actions.delete(this);
	});
}

ActionManager.prototype.doAction=function(key) {
	$(this.actions).each(function(){
		if(this.key == key)
			this.cb(key,this.cb);
	});
}

window.actionManager = new ActionManager();
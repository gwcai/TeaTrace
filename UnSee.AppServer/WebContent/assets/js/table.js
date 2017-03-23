function toggleAll(el, checked) {
	$(el).prop("checked",checked);
}

function findNearRowObject(fromEl) {
	if(fromEl == null) return null;
	if($(fromEl).is("tr")) return fromEl;
	return findNearRowObject($(fromEl).parent());
}

function objectToTable(obj, css) {
	if(typeof obj === 'string') return obj;
	var table = "<table "+(css?("class='"+css+"'"):"")+">";
	for(var o in obj) {
		table += "<tr>";
		table += "<td>"+o+"</td>";
		table += "<td>"+(obj[o]||"")+"</td>";
		table += "</tr>";
	}
	table += "</table>";
	
	return table;
}

Array.prototype.toHTMLTable = function(css) {
	css = css||"table";
	var table = "<table "+(css?("class='"+css+"'"):"")+">";
	var columns = [];
	$(this).each(function(){
		for(var o in this) {
			if(columns.indexOf(o) == -1)
				columns.push(o);
		}
	});

	table += "<thead><tr>"
	$(columns).each(function(){
		table += "<td>"+this+"</td>"
	});
	table += "</tr></thead>"

	table += "<tbody>"
	$(this).each(function(){
		var data = this;
		table += "<tr>";
		$(columns).each(function(){
			var v=(data[this]||"");
			table += "<td>"+objectToTable(v, "table")+"</td>";
		});
		table += "</tr>";
	});
	table += "</tbody></table>";
	
	return table;
}
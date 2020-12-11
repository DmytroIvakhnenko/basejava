function saveInputValues(parent) {
    let elements = parent.getElementsByTagName("input");
    for (let i = 0; i < elements.length; i++) {
        // set attribute to property value
        elements[i].setAttribute("value", elements[i].value);
    }
}

function getLastIndex(parent) {
    let index = 0;
    if (parent.lastElementChild != null) {
        index = parent.lastElementChild.id.match(/[0-9]+$/);
        if (index == null) {
            index = 0;
        }
    }
    return index;
}

function addField(parentId) {
    const parent = document.getElementById(parentId);
    const idx = 1 + parseInt(getLastIndex(parent));
    saveInputValues(parent);
    let fieldHtml = `<dd id="${parentId + idx}"><input type="text" id="${parentId + idx}" name="${parentId}" size=100 value="">
                         <a href="javascript:void(0)" title="Delete field" onclick="remove('${parentId + idx}')"><img alt="Delete" src="img/delete.png"></a>
                    </dd>`;
    parent.innerHTML += fieldHtml;
}

function addPosition(parentId) {
    const parent = document.getElementById(parentId);
    const idx = 1 + parseInt(getLastIndex(parent));
    saveInputValues(parent);
    let posHtml = `<dl id="${parentId + ".positions" + idx}">
                        <dt>Position: <a href="javascript:void(0)" title="Delete position"
                                         onclick="removePosition('${parentId + ".positions" + idx}')"><img alt="Delete" src="img/delete.png"></a></dt>
                        <dd><input type="text" id="${parentId + ".position" + idx}" name="${parentId + ".position"}" size=50 value="" required></dd>
                        <dt>Description:</dt>
                        <dd><input type="text" id="${parentId + ".description" + idx}" name="${parentId + ".description"}" size=50 value=""></dd>
                        <br>
                        <dt>Start date:</dt>
                        <dd><input type="text" id="${parentId + ".startDate" + idx}" name="${parentId + ".startDate"}" size=10 value="" placeholder="YYYY-MM" required></dd>
                        <dt>End date:</dt>
                        <dd><input type="text" id="${parentId + ".endDate" + idx}" name="${parentId + ".endDate"}" size=10 value="" placeholder="YYYY-MM" required></dd>
                    </dl>`
    parent.innerHTML += posHtml;
}

function addExperience(parentId) {
    const parent = document.getElementById(parentId);
    const idx = 1 + parseInt(getLastIndex(parent));
    saveInputValues(parent);
    let expHtml = `<dl id="${parentId + idx}">
                        <dt><b>Organization: <a href="javascript:void(0)" title="Delete place" onclick="remove('${parentId + idx}')"><img alt="Delete" src="img/delete.png"></a></b></dt>
                        <dd><input type="text" id="${parentId + idx + ".place"}" name="${parentId + ".place"}" size=50 value="" required></dd>
                        <dt><b>Homepage:</b></dt>
                        <dd><input type="text" id="${parentId + idx + ".homepage"}" name="${parentId + ".homepage"}" size=50 value=""></dd>
                        <br>
                        <a href="javascript:void(0)" title="Add position" onclick="addPosition('${parentId + idx}')"><img alt="Add" src="img/add.png"></a>`;
    parent.innerHTML += expHtml;
    addPosition(parentId + idx);
    parent.innerHTML += "</dl>";
}

function remove(id) {
    let element = document.getElementById(id);
    element.remove();
}

function removePosition(id) {
    const element = document.getElementById(id);
    const posCount = element.parentElement.getElementsByTagName("dl").length;
    if (posCount <= 1) {
        window.alert("Experience and Education sections must have at least 1 position")
    } else {
        element.remove();
    }
}

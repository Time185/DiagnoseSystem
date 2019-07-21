/**
 * Application GUI.
 */

// Default colour maps.
dwv.tool.colourMaps = {
    "plain": dwv.image.lut.plain,
    "invplain": dwv.image.lut.invPlain,
    "rainbow": dwv.image.lut.rainbow,
    "hot": dwv.image.lut.hot,
    "hotiron": dwv.image.lut.hot_iron,
    "pet": dwv.image.lut.pet,
    "hotmetalblue": dwv.image.lut.hot_metal_blue,
    "pet20step": dwv.image.lut.pet_20step
};
// Default window level presets.
dwv.tool.defaultpresets = {};
// Default window level presets for CT.
dwv.tool.defaultpresets.CT = {
    "mediastinum": {"center": 40, "width": 400},
    "lung": {"center": -500, "width": 1500},
    "bone": {"center": 500, "width": 2000},
    "brain": {"center": 40, "width": 80},
    "head": {"center": 90, "width": 350}
};

//decode query
dwv.utils.decodeQuery = function (query, callback)
{
    if (query.type === "gdrive") {
        var gAuth = new dwv.google.Auth();
        var gDrive = new dwv.google.Drive();
        gDrive.setIds( query.input.split(',') );
        // pipeline
        gAuth.onload = gDrive.load;
        gAuth.onfail = function () {
            $("#popupAuth").popup("open");
            var authorizeButton = document.getElementById('gauth-button');
            // explicit auth from button to allow popup
            authorizeButton.onclick = function() {
                $("#popupAuth").popup("close");
                gAuth.load();
            };
        };
        gDrive.onload = dwv.google.getAuthorizedCallback(callback);
        // launch with silent auth
        gAuth.loadSilent();
    }
    else {
        // default
        dwv.utils.base.decodeQuery(query, callback);
    }
};

// Window
dwv.gui.getWindowSize = function () {
    return { 'width': ($(window).width()), 'height': ($(window).height() - 147) };
};
// Prompt
dwv.gui.prompt = dwv.gui.base.prompt;
// Progress
/* global NProgress */
dwv.gui.displayProgress = function (percent) {
    NProgress.configure({ showSpinner: false });
    if( percent < 100 ) {
        //$.mobile.loading("show", {text: percent+"%", textVisible: true, theme: "b"} );
        NProgress.set(percent/100);
    }
    else if( percent >= 100 ) {
        //$.mobile.loading("hide");
        NProgress.done();
    }
};
// Focus
dwv.gui.focusImage = function ()
{
    $.mobile.changePage("#main");
};
// get element
dwv.gui.getElement = dwv.gui.base.getElement;
// refresh
dwv.gui.refreshElement = function (element) {
    if( $(element)[0].nodeName.toLowerCase() === 'select' ) {
        $(element).selectmenu('refresh');
    }
    else {
        $(element).enhanceWithin();
    }
};
// Slider
dwv.gui.Slider = dwv.gui.base.Slider;
// plot
dwv.gui.plot = function (div, data, options)
{
    var plotOptions = {
        "bars": { "show": true },
        "grid": { "backgroundcolor": null },
        "xaxis": { "show": true },
        "yaxis": { "show": false }
    };
    if (typeof options !== "undefined" &&
        typeof options.markings !== "undefined") {
        plotOptions.grid.markings = options.markings;
    }
    $.plot(div, [ data ], plotOptions);
};
// Post process table
dwv.gui.postProcessTable = function (table)
{
    var tableClass = table.className;
    // css
    table.className += " table-stripe ui-responsive";
    // add columntoggle
    table.setAttribute("data-role", "table");
    table.setAttribute("data-mode", "columntoggle");
    table.setAttribute("data-column-btn-text", dwv.i18n("basics.columns") + "...");
    // add priority columns for columntoggle
    var addDataPriority = function (cell) {
        var text = cell.firstChild.data;
        if ( tableClass === "tagsTable" ) {
            if ( text !== "value" && text !== "name" ) {
                cell.setAttribute("data-priority", "5");
            }
        }
        else if ( tableClass === "drawsTable" ) {
            if ( text === "description" ) {
                cell.setAttribute("data-priority", "1");
            }
            else if ( text === "frame" || text === "slice" ) {
                cell.setAttribute("data-priority", "5");
            }

        }
    };
    if (table.rows.length !== 0) {
        var hCells = table.rows.item(0).cells;
        for (var c = 0; c < hCells.length; ++c) {
            addDataPriority(hCells[c]);
        }
    }
    // return
    return table;
};
// Tags table
dwv.gui.DicomTags = dwv.gui.base.DicomTags;
// DrawList table
dwv.gui.DrawList = dwv.gui.base.DrawList;

// Loaders
dwv.gui.Loadbox = dwv.gui.base.Loadbox;
// File loader
dwv.gui.FileLoad = dwv.gui.base.FileLoad;
dwv.gui.FileLoad.prototype.onchange = function (/*event*/) {
    $("#popupOpen").popup("close");
};
// Folder loader
dwv.gui.FolderLoad = dwv.gui.base.FolderLoad;
dwv.gui.FolderLoad.prototype.onchange = function (/*event*/) {
    $("#popupOpen").popup("close");
};
// Url loader
dwv.gui.UrlLoad = dwv.gui.base.UrlLoad;
dwv.gui.UrlLoad.prototype.onchange = function (/*event*/) {
    $("#popupOpen").popup("close");
};

// Toolbox
dwv.gui.Toolbox = function (app)
{
    var base = new dwv.gui.base.Toolbox(app);

    this.setup = function (list)
    {
        base.setup(list);

        // toolbar
        var buttonClass = "ui-btn ui-btn-inline ui-btn-icon-top ui-mini";
        //var buttonClass1 = "ui-btn ui-btn-inline ui-btn-icon-top ui-mini";
        
        var deleteButton = document.createElement("a");
        var deleteButtonText= document.createTextNode("删除标注 ");
        deleteButton.appendChild(deleteButtonText);
        deleteButton.setAttribute("class", buttonClass + " ui-icon-delete");
        deleteButton.onclick=app.onDeleteCurrentCanvas;
        deleteButton.title = dwv.i18n("basics.deleteButton");
        

        var nextButton = document.createElement("a");
        var nextButtonText= document.createTextNode("向下翻页 ");
        nextButton.appendChild(nextButtonText);
        nextButton.setAttribute("class", buttonClass + " ui-icon-arrow-d");
        nextButton.onclick=app.onNext;
        nextButton.title = dwv.i18n("basics.nextButton");
        
        var lastButton = document.createElement("a");
        var lastButtonText= document.createTextNode("向上翻页 ");
        lastButton.appendChild(lastButtonText);
        lastButton.setAttribute("class", buttonClass + " ui-icon-arrow-u");
        lastButton.onclick=app.onLast;
        lastButton.title = dwv.i18n("basics.lastButton");
        
        var undo = document.createElement("a");
        var undoButtonText= document.createTextNode("向前回滚 ");
        undo.appendChild(undoButtonText);
        undo.setAttribute("class", buttonClass + " ui-icon-back");
        undo.onclick = app.onUndo;
        undo.title = dwv.i18n("basics.undo");

        var redo = document.createElement("a");
        var redoButtonText= document.createTextNode("向后回滚 ");
        redo.appendChild(redoButtonText);
        redo.setAttribute("class", buttonClass + " ui-icon-forward");
        redo.onclick = app.onRedo;
        redo.title = dwv.i18n("basics.redo");

        var toggleInfo = document.createElement("a");
        var toggleInfoButtonText= document.createTextNode("DCM信息");
        toggleInfo.appendChild(toggleInfoButtonText);
        toggleInfo.setAttribute("class", buttonClass + " ui-icon-info");
        toggleInfo.onclick = app.onToggleInfoLayer;
        toggleInfo.title = dwv.i18n("basics.toggleInfo");

        var toggleSaveState = document.createElement("a");
        var toggleSaveStateButtonText= document.createTextNode("保存标注 ");
        toggleSaveState.appendChild(toggleSaveStateButtonText);
        toggleSaveState.setAttribute("class", buttonClass + " download-state ui-icon-action");
        toggleSaveState.onclick = app.onStateSave;
        //toggleSaveState.download = "state.json";
        toggleSaveState.title = dwv.i18n("basics.toggleSaveState");
        

        var tags = document.createElement("a");
        var tagsButtonText= document.createTextNode("DCM标签");
        tags.appendChild(tagsButtonText);       
        tags.href = "#tags_page";
        tags.setAttribute("class", buttonClass + " ui-icon-grid");
        tags.title = dwv.i18n("basics.dicomTags");

        var drawList = document.createElement("a");
        var drawListButtonText= document.createTextNode("添加注释 ");
        drawList.appendChild(drawListButtonText); 
        drawList.href = "#drawList_page";
        drawList.setAttribute("class", buttonClass + " ui-icon-edit");
        drawList.title = dwv.i18n("basics.drawList");
        
        var labelInfo=document.createElement("label");
        labelInfo.id="labelNow";
        labelInfo.style="float:right";
 

        var node = app.getElement("toolbar");
        var localtion=app.getElement("localtion");
        localtion.appendChild(labelInfo);
        node.appendChild(deleteButton);
        node.appendChild(undo);
        node.appendChild(redo);
        node.appendChild(toggleInfo);
        node.appendChild(toggleSaveState);
        node.appendChild(tags);
        node.appendChild(drawList);
//        node.appendChild(lastButton);
//        node.appendChild(nextButton);

        dwv.gui.refreshElement(node);
    };
    this.display = function (flag)
    {
        base.display(flag);
    };
    this.initialise = function (list)
    {
        base.initialise(list);
    };
    function mouseScroll (up) {
        var hasSlices = (app.getImage().getGeometry().getSize().getNumberOfSlices() !== 1);
        var hasFrames = (app.getImage().getNumberOfFrames() !== 1);
        if ( up ) {
            if (hasSlices) {
                app.getViewController().incrementSliceNb();
            } else if (hasFrames) {
                app.getViewController().incrementFrameNb();
            }
        } else {
            if (hasSlices) {
                app.getViewController().decrementSliceNb();
            } else if (hasFrames) {
                app.getViewController().decrementFrameNb();
            }
        }
    }
};

// Window/level
dwv.gui.WindowLevel = dwv.gui.base.WindowLevel;
// Draw
dwv.gui.Draw = dwv.gui.base.Draw;
// ColourTool
dwv.gui.ColourTool = dwv.gui.base.ColourTool;
// ZoomAndPan
dwv.gui.ZoomAndPan = dwv.gui.base.ZoomAndPan;
// Scroll
dwv.gui.Scroll = dwv.gui.base.Scroll;
// Filter
dwv.gui.Filter = dwv.gui.base.Filter;

// Filter: threshold
dwv.gui.Threshold = dwv.gui.base.Threshold;
// Filter: sharpen
dwv.gui.Sharpen = dwv.gui.base.Sharpen;
// Filter: sobel
dwv.gui.Sobel = dwv.gui.base.Sobel;

// Undo/redo
dwv.gui.Undo = dwv.gui.base.Undo;
// Help
dwv.gui.appendHelpHtml = dwv.gui.base.appendHelpHtml;
// Version
dwv.gui.appendVersionHtml = dwv.gui.base.appendVersionHtml;

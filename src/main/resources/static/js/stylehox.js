document.oncontextmenu = function (e) {
    return false;
}
document.onpaste = function (e) {
    return false;
}
document.oncopy = function (e) {
    return false;
}
document.oncut = function (e) {
    return false;
}
document.onselectstart = function (e) {
    return false;
}

// document.onkeydown = KeyDown;
document.onmousedown = scrollstop;
// document.ondblclick = scrollstart;
// var pageActionUrl = (window.location.href.indexOf("mynovel") > 0) ? "http://myaction.novel.xiaoyushu.com" : "http://action.novel.xiaoyushu.com";

function dollar(id) {
    if (document.getElementById) {
        return document.getElementById(id);
    }
    else if (document.all) {
        return document.all[id];
    }
    else if (document.layers) {
        return document.layers[id];
    }
    else {
        return false;
    }
}

function dollar1(id) {
    try {
        return dollar(id).style;
    } catch (e) {
    }
}

function dollar2(id) {
    if (document.getElementsByTagName) {
        return document.getElementsByTagName(id);
    }
}

function BlockO(id) {
    if (IsObj(id) == true) dollar1(id).display = "block";
}

function HiddenO(id) {
    if (IsObj(id) == true) dollar1(id).display = "none";
}

function OHtml(id) {
    return dollar(id).innerHTML;
}

function setOHtml(id, txt) {
    if (IsObj(id) == true) dollar(id).innerHTML = txt;
}

function GetRandomNum() {
    var Rand = Math.random();
    return (Math.round(Rand * 1000));
}

function IsObj(obj) {
    if (dollar(obj)) {
        if (typeof(dollar(obj)) == "undefined")
            return false; else
            return true;
    }
    else
        return false;
}

var OsName = getOs();

function getOs() {
    var _nav = navigator.userAgent;
    if (_nav.indexOf("MSIE") > 0)
        return "MSIE"; else if (_nav.indexOf("Firefox") > 0)
        return "Firefox"; else if (_nav.indexOf("Safari") > 0)
        return "Safari"; else if (_nav.indexOf("Camino") > 0)
        return "Camino"; else if (_nav.indexOf("Gecko/") > 0)
        return "Gecko"; else
        return "MSIE";
}

function IsIE() {
    return (OsName == "MSIE") ? true : false;
}

function GetIEVersion() {
    var v = navigator.userAgent;
    if (v.indexOf("MSIE 6.0") > 0) v = 6; else if (v.indexOf("MSIE 7.0") > 0) v = 7
    else if (v.indexOf("MSIE 8.0") > 0) v = 8; else if (v.indexOf("MSIE 9.0") > 0) v = 9;
    return v;
}

function classCommonDiv() {
    this.ifrmHeight = "320";
    this.ifrmWidth = "520";
    this.ifrmTop = "200";
    this.ifrmUrl = "";
    this.scrolling = "no";
    this.Show = function () {
        this.ifrmHeight = "320";
        this.ifrmWidth = "520";
        this.ifrmTop = "200";
        this.scrolling = "no";
        this.ShowSize();
    }
    this.ShowSize = function () {
        BlockO("jsifrm");
        if (OsName == "MSIE") {
            if (GetIEVersion() == 6) {
                dollar1("jsifrm").top = eval(document.documentElement.scrollTop) + eval(this.ifrmTop);
                dollar1("jsifrm").position = "absolute";
                dollar1("jsallscreen").height = document.body.scrollHeight;
            }
            else {
                dollar1("jsifrm").position = "fixed";
                dollar1("jsifrm").top = this.ifrmTop + "px";
            }
            dollar1("jsifrm").left = (window.screen.width - this.ifrmWidth) / 2 + "px";
        }
        else {
            dollar1("jsifrm").top = this.ifrmTop + "px";
            dollar1("jsifrm").position = "fixed";
            dollar1("jsifrm").display = 'table';
            dollar1("jsifrm").left = (window.screen.width - this.ifrmWidth) / 2 + "px";
        }
        BlockO("jsallscreen");
        dollar("jsifrm").innerHTML = "<iframe width='" + this.ifrmWidth + "px' height='" + this.ifrmHeight + "px' marginwidth='0' marginheight='0' scrolling='" + this.scrolling + "' border='0' frameborder='0' id='jsiframeonclass' ></iframe>";
        dollar("jsiframeonclass").src = this.ifrmUrl;
    }
    this.Hidden = function () {
        HiddenO("jsifrm");
        HiddenO("jsallscreen");
    }
}

var IsAutoPage = 0;
var scrollspeed = 50;
var scrollval = null;
var PREVIOUS_PAGE = "null";
var NEXT_PAGE = "null";
var bg, size, color, width, speed, autopage, line;

function getReadSet() {
    setFontSize(size);
    setBgColor(bg);
    setFontColor(color);
    setWidth(width);
    setAutopage(autopage);
    setSpeed(speed);
    setLine(line);
}

function setAutopage(value) {
    if (value == true)
        IsAutoPage = 1; else if (value == false)
        IsAutoPage = 0; else
        IsAutoPage = value;
    if (IsAutoPage == "1")
        dollar("autopage").checked = true; else
        dollar("autopage").checked = false;
    if (IsAutoPage != autopage)
        SetCookie("autopage", IsAutoPage);
}

function setSpeed(value) {
    scrollspeed = value;
    if (value != speed)
        SetCookie("speed", value);
    if (IsAutoPage == "1")
        scrollstart();
}

function setFontSize(type) {
    if (type == "")
        return null;
    dollar1("htmlContent").fontSize = type + "px";
    if (type != size)
        SetCookie("size", type);
    try {
        for (i = 0; i < dollar2("img").length; i++) {
            if (dollar2("img")[i].className == "ci") {
                if (eval(type) < 14)
                    dollar2("img")[i].style.height = eval(type - (-2)) + "px"; else if ((eval(type) >= 14) & (eval(type) < 20))
                    dollar2("img")[i].style.height = ""; else
                    dollar2("img")[i].style.height = eval(type - 2) + "px";
            }
        }
    }
    catch (e) {
    }
}

function setBgColor(mycolor) {
    if (mycolor == "")
        mycolor = "#EFF6FC";
    dollar1("articleContent").background = mycolor;
    if (mycolor != bg)
        SetCookie("bg", mycolor);
    try {
        for (i = 0; i < dollar2("font").length; i++) {
            if (dollar2("font")[i].className == "cp") {
                dollar2("font")[i].style.color = mycolor;
            }
        }
    }
    catch (e) {
    }
}

function setFontColor(mycolor) {
    if (mycolor == "")
        mycolor = "#000000";
    dollar1("htmlContent").color = mycolor;
    if (mycolor != color)
        SetCookie("color", mycolor);
    try {
        if (test != null)
            test.document.body.style.color = mycolor;
    } catch (e) {
    }
}

function setWidth(mywidth) {
    if (mywidth == "")
        dollar1('htmlContent').width = "";
    else
        dollar1('htmlContent').width = mywidth + "%";
    if (mywidth != width)
        SetCookie("width", mywidth);
}

function setLine(myline) {
    if (myline == "")
        dollar1('htmlContent').lineHeight = "34px";
    else
        dollar1('htmlContent').lineHeight = myline + "px";
    if (myline != line)
        SetCookie("line", myline);
}

function SetCookie(name, value) {
    var Then = new Date()
    Then.setTime(Then.getTime() + 60 * 60 * 24 * 1000)
    var _array = new Array(bg, size, color, width, speed, autopage, line);
    if (name == "bg") {
        _array[0] = value;
        bg = value;
    }
    else if (name == "size") {
        _array[1] = value;
        size = value;
    }
    else if (name == "color") {
        _array[2] = value;
        color = value;
    }
    else if (name == "width") {
        _array[3] = value;
        width = value;
    }
    else if (name == "speed") {
        _array[4] = value;
        speed = value;
    }
    else if (name == "autopage") {
        _array[5] = value;
        autopage = value;
    }
    else if (name == "line") {
        _array[6] = value;
        line = value;
    }
    var _value = "PageStyle=" + _array[0] + "|" + _array[1] + "|" + _array[2] + "|" + _array[3] + "|" + _array[4] + "|" + _array[5] + "|" + _array[6];
    document.cookie = _value + ";expires=" + Then.toGMTString();
}

function GetStyleCookie() {
    var _str = getCookie("PageStyle");
    _v = _str.split("|")
    bg = _v[0];
    if (!bg) bg = "";
    size = _v[1];
    if (!size) size = "";
    color = _v[2];
    if (!color) color = "";
    width = _v[3];
    if (!width) width = "";
    speed = _v[4];
    if (!speed) speed = "";
    autopage = _v[5];
    if (!autopage) autopage = "";
    line = _v[6];
    if (!line) line = "";
    setFontSize(size);
    setFontColor(color);
    setLine(line);
    setSpeed(speed);
    setAutopage(autopage);
    setBgColor(bg);
}


function scrollstop() {
    clearInterval(scrollval);
}

function scrollmove() {
    window.scrollBy(0, 1);
    if (document.documentElement.scrollTop == document.documentElement.scrollHeight - 1200) {
        scrollstop();
        scrollturnpage();
    }
}

function scrollturnpage() {
    if (IsAutoPage == "1")
        setTimeout("window.location.href = NEXT_PAGE", 500);
}

function WriteSelect() {
    document.write("<select onchange='javascript:setWidth(this.value);' id='scrollspeed' name='scrollspeed' style='display:none;'>");
    document.write("<option value='100%'>宽度</option>");
    document.write("<option value='' style='background-color: #ffffed'>默认</option>");
    for (var i = 9; i > 5; i--) {
        document.write("<option value='" + i + "0'>" + i + "0%</option>");
    }
    document.write("</select>");
    document.write("<select name='txtsize' id='txtsize' onchange='javascript:setFontSize(this.value)'>");
    document.write("<option value='' selected='selected'>字号</option>");
    document.write("<option value='14' style='background-color: #ffffed'>默认</option>");
    for (var i = 6; i <= 9; i++) {
        document.write("<option value='" + i * 2 + "'>" + i * 2 + "px</option>");
    }
    document.write("<option value='20'>20pt</option>");
    document.write("<option value='24'>24pt</option>");
    document.write("</select>");
    document.write("<select name='bcolor' id='bcolor' onchange='javascript:setBgColor(this.value);'>");
    document.write("<option value=''>底色</option>");
    document.write("<option style='background-color: #EFF6FC' value='#EFF6FC'>默认</option>");
    document.write("<option style='background-color: #e6f3ff' value='#e6f3ff'>蓝色</option>");
    document.write("<option style='background-color: #ffffff' value='#ffffff'>白色</option>");
    document.write("<option style='background-color: #e4ebf1' value='#e4ebf1'>淡蓝</option>");
    document.write("<option style='background-color: #eeeeee' value='#eeeeee'>淡灰</option>");
    document.write("<option style='background-color: #eaeaea' value='#eaeaea'>灰色</option>");
    document.write("<option style='background-color: #e4e1d8' value='#e4e1d8'>深灰</option>");
    document.write("<option style='background-color: #e6e6e6' value='#e6e6e6'>暗灰</option>");
    document.write("<option style='background-color: #eefaee' value='#eefaee'>绿色</option>");
    document.write("<option style='background-color: #ffffed' value='#ffffed'>明黄</option>");
    document.write("</select>");
    document.write("<select name='txtcolor' id='txtcolor' onchange='setFontColor(this.value)'>");
    document.write("<option value=''>字色</option>");
    document.write("<option value='#000000' style='background-color: #ffffed;color:#000'>黑色</option>");
    document.write("<option value='#ff0000' style='color:#f00;'>红色</option>");
    document.write("<option value='#006600' style='color:#060'>绿色</option>");
    document.write("<option value='#0000ff' style='color:#00f'>蓝色</option>");
    document.write("<option value='#660000' style='color:#600'>棕色</option>");
    document.write("</select>");
    document.write("<select name='txtline' id='txtline' onchange='setLine(this.value)'>");
    document.write("<option value=''>行距</option>");
    document.write("<option value='28'>默认</option>");
    document.write("<option value='50'>特大</option>");
    document.write("<option value='36'>大</option>");
    document.write("<option value='28'>中</option>");
    document.write("<option value='24'>小</option>");
    document.write("</select>");
    // document.write("<select name='winscrll' id='winscrll' onchange='javascript:setSpeed(this.value);'>");
    // document.write("<option value='50'>滚屏</option>");
    // document.write("<option value='90'>1慢</option>");
    // document.write("<option value='80'>2</option>");
    // document.write("<option value='70'>3</option>");
    // document.write("<option value='60'>4</option>");
    // document.write("<option value='50'>5</option>");
    // document.write("<option value='40'>6</option>");
    // document.write("<option value='30'>7</option>");
    // document.write("<option value='20'>8</option>");
    // document.write("<option value='10'>9快</option>");
    // document.write("</select>");
    document.write("<input type='checkbox' onclick='javascript:setAutopage(this.checked);' id='autopage' name='autopage'/>");
}


function getCookiesWithKey(key, c_name) {
    if (document.cookie.length > 0) {
        var k_start = document.cookie.indexOf(key + "=");
        if (k_start == -1)
            return "";
        k_start = k_start + key.length + 1
        var k_end = document.cookie.indexOf(";", k_start);
        if (k_end == -1) k_end = document.cookie.length;
        var cookiesWithKey = unescape(document.cookie.substring(k_start, k_end));
        if (c_name == "") return cookiesWithKey;
        var cookies = cookiesWithKey.split("&");
        for (var i = 0; i < cookies.length; i++) {
            if (cookies[i].split("=")[0] == c_name) {
                return cookies[i].split("=")[1];
            }
        }
    }
    return ""
}

function getCookie(name) {
    return getCookiesWithKey(name, "")
}

function IsLoginIn() {
    var uname = getCookiesWithKey("21rednet", "UserName");
    if (uname == null)
        return false; else if (uname == "")
        return false; else
        return true;
}
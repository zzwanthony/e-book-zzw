function addBookmark(title,url) {
    if(!title){title =document.title};
    if(!url){url=window.location.href}
    if (window.sidebar) {
        window.sidebar.addPanel(title,url ,"");
    } else if( document.all ) {
        window.external.AddFavorite(url,title);
    } else if( window.opera || window.print ) {
        alert("对不起，您的浏览器不支持此操作！请您使用菜单栏或Ctrl+D收藏本站。");
        return true;
    }
}
function killErrors() {
    return true;
}
window.onerror = killErrors;
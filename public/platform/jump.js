function addActiveAndWindowName() {
    const href = window.location.href;
    const plat = href.split('/')[4];
    $("#" + plat).addClass("action");

    if (window.name === "" && plat === "platformHome") {
        let uuid = random4() + random4();
        window.name = uuid;
    }
}

function random4() {
    return ((1 + Math.random()) * 0x10000 | 0).toString(16).substring(1);
}

const host = window.location.hostname;

const diversityHtml = "http://" + host + ":8001/diversity/project/home";

$(".diversity").click(function () {
    $.cookie("diversity" + window.name, diversityHtml);
});

const parametronHtml = "http://" + host + ":8002/project/home";

$(".parametron").click(function () {
    $.cookie("parametron" + window.name, parametronHtml)
});

const transcriptomeHtml = "http://" + host + ":8003/transcriptome/project/home";

$(".transcriptome").click(function () {
    $.cookie("transcriptome" + window.name, transcriptomeHtml)
});

const networkHtml = "http://" + host + ":8004/network/intra/intraPage";

$(".network").click(function () {
    $.cookie("network" + window.name, networkHtml)
});

const p3bacterHtml = "http://" + host + ":8005/p3bacter/user/assemblePredictBefore";

$(".p3bacter").click(function () {
    $.cookie("p3bacter" + window.name, p3bacterHtml)
});

const proteinHtml = "http://" + host + ":8006/protein/project/projectManageBefore";

$(".protein").click(function () {
    $.cookie("protein" + window.name, proteinHtml)
});

const metaboliteHtml = "http://" + host + ":8007/metabolite/user/project/projectManageBefore";

$(".metabolite").click(function () {
    $.cookie("metabolite" + window.name,metaboliteHtml)
});

const metaHtml = "http://" + host + ":8008/meta/project/projectPage";

$(".meta").click(function () {
    $.cookie("meta" + window.name, metaHtml)
});



function addCookie(plat) {
    if (window.name === "") {
        let uuid2 = random4() + random4();
        window.name = uuid2;
        let cookie1 = plat + window.name;
        let html = "";
        if (plat === "diversity") {
            html =  diversityHtml
        } else if (plat === "parametron") {
            html = parametronHtml
        } else if (plat === "transcriptome") {
            html =  transcriptomeHtml
        } else if (plat === "network") {
            html =  networkHtml
        } else  if(plat === "meta"){
            html = metaHtml
        } else if(plat === "protein"){
            html = proteinHtml
        } else if(plat === "metabolite"){
            html = metaboliteHtml
        } else if(plat === "p3bacter"){
            html = p3bacterHtml
        }
        $.cookie(cookie1, html)
    }

//监听事件，从子页面接受页面路由信息，当页面发生切换时，同时改变cookie值，以便刷新时页面还在当前页
    window.addEventListener('message', function (e) {
        let src = e.data;
        $.cookie(cookie1, src);
    }, false);
}


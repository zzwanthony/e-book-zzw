$(function () {
    var step = $("#myStep").step({
        animate: true,
        initStep: 1,
        speed: 1000
    });
    $("#preBtn").click(function (event) {
        var yes = step.preStep();

    });
    $("#applyBtn").click(function (event) {

        var code = $.trim($("#Verification").val());
        var phone = /[1][3-9][0-9]{9,9}/;
        var phones = $.trim($("#phone").val());
        if ($.trim(phones) == "") {
            Tip('请填写手机号码！');
            $("#phone").focus();
            return;
        }
        if (!phone.exec(phones)) {

            Tip('手机输入格式不正确,请从新输入');
            $("#phones").focus();
            return;
        }
        if ($.trim(code) == "") {
            Tip('验证码未填写！');
            $("#Verification").focus();
            return;
        }
        var result="";
        $.ajax({
            type: "GET",
            url: "/verifyCode.do/" + $.trim(phones) +"/"+ $.trim(code),
            dataType: "json",
            success: function (data) {
                result = data;
                if(data == "success"){
                    var yes = step.nextStep();
                }else if(data == "phoneno"){
                    Tip('手机号已经注册！');
                    $(".captcha").click();
                    $("#phones").val("");
                    $("#phones").focus();
                }else if(data == "codeno"){
                    Tip('验证码输入错误！');
                    $(".captcha").click();
                    $("#Verification").focus();
                }
            },error: function (data) {
                Tip('请求出错！');
                $(".captcha").click();
            }
        });
        return;
    });
    $("#submitBtn").click(function (event) {
        var txtconfirm = $.trim($("#confirmpwd").val());
        var txtPwd = $("#password").val();
        var phones = $.trim($("#phone").val());

        if ($.trim(txtPwd) == "") {
            Tips('请输入你要设置的密码！');
            $("#txtPwd").focus();
            return;
        }
        if ($.trim(txtPwd).length < 6) {
            Tips('密码长度不能小于6位数！');
            $("#txtPwd").focus();
            return;
        }
        if ($.trim(txtconfirm) == "") {
            Tips('请再次输入密码！');
            $("#txtconfirm").focus();
            return;
        }
        if ($.trim(txtconfirm) != $.trim(txtPwd)) {
            Tips('你输入的密码不匹配，请从新输入！');
            $("#txtconfirm").focus();
            return;
        }
        $.ajax({
            type: "POST",
            url: "/register.do",
            data: {phone: phones, password: txtconfirm},
            dataType: "json",
            success: function (data) {
                if (data == "success") {
                    var yes = step.nextStep();
                    // setTimeout("lazyGo();", 1000);
                    // function lazyGo() {
                    //     var sec = $("#sec").text();
                    //     $("#sec").text(--sec);
                    //     if (sec > 0)
                    //         setTimeout("lazyGo();", 1000);
                    //     else
                    //         window.location.href = "/";
                    // }
                } else if (data == "no") {
                    Tips('注册出错！');
                }
            },
            error: function (data) {
                //请求出错
                Tip('请求出现错误！');
            }
        });

    });
    $("#goBtn").click(function (event) {
        var yes = step.goStep(3);
    });
});
(function (factory) {
        "use strict";
        if (typeof define === 'function') {
            // using CMD; register as anon module
            define.cmd && define('jquery-step', ['jquery'], function (require, exports, moudles) {
                var $ = require("jquery");
                factory($);
                return $;
            });
            define.amd && define(['jquery'], factory);
        } else {
            // no CMD; invoke directly
            factory((typeof(jQuery) != 'undefined') ? jQuery : window.Zepto);
        }
    }

    (function ($) {
        $.fn.step = function (options) {
            var opts = $.extend({}, $.fn.step.defaults, options);
            var size = this.find(".step-header li").length;
            var barWidth = opts.initStep < size ? 100 / (2 * size) + 100 * (opts.initStep - 1) / size : 100;
            var curPage = opts.initStep;

            this.find(".step-header").prepend("<div class=\"step-bar\"><div class=\"step-bar-active\"></div></div>");
            this.find(".step-list").eq(opts.initStep - 1).show();
            if (size < opts.initStep) {
                opts.initStep = size;
            }
            if (opts.animate == false) {
                opts.speed = 0;
            }
            this.find(".step-header li").each(function (i, li) {
                if (i < opts.initStep) {
                    $(li).addClass("step-active");
                }
                //$(li).prepend("<span>"+(i+1)+"</span>");
                $(li).append("<span>" + (i + 1) + "</span>");
            });
            this.find(".step-header li").css({
                "width": 100 / size + "%"
            });
            this.find(".step-header").show();
            this.find(".step-bar-active").animate({
                    "width": barWidth + "%"
                },
                opts.speed, function () {

                });

            this.nextStep = function () {
                if (curPage >= size) {
                    return false;
                }
                return this.goStep(curPage + 1);
            }

            this.preStep = function () {
                if (curPage <= 1) {
                    return false;
                }
                return this.goStep(curPage - 1);
            }

            this.goStep = function (page) {
                if (page == undefined || isNaN(page) || page < 0) {
                    if (window.console && window.console.error) {
                        console.error('the method goStep has a error,page:' + page);
                    }
                    return false;
                }
                curPage = page;
                this.find(".step-list").hide();
                this.find(".step-list").eq(curPage - 1).show();
                this.find(".step-header li").each(function (i, li) {
                    $li = $(li);
                    $li.removeClass('step-active');
                    if (i < page) {
                        $li.addClass('step-active');
                        if (opts.scrollTop) {
                            $('html,body').animate({scrollTop: 0}, 'slow');
                        }
                    }
                });
                barWidth = page < size ? 100 / (2 * size) + 100 * (page - 1) / size : 100;
                this.find(".step-bar-active").animate({
                        "width": barWidth + "%"
                    },
                    opts.speed, function () {

                    });
                return true;
            }
            return this;
        };

        $.fn.step.defaults = {
            animate: true,
            speed: 500,
            initStep: 1,
            scrollTop: true
        };
    })
);
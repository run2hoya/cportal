<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<script charset="utf-8">
    function numbersOnly(event, decimal) {
        event = event || window.event;
        var keyID = (event.which) ? event.which : event.keyCode;

        if ((keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105) || keyID == 8 || keyID == 9 || keyID == 46 || keyID == 37 || keyID == 39 ||
            ((keyID == 17) || (keyID == 86)) || ((keyID == 17) || (keyID == 67)))
            return;
        else
            return false;
    }

    function maxLengthCheck(object) {
        if (object.value.length > object.maxLength)
            object.value = object.value.slice(0, object.maxLength)
    }

    function specialChars(tag, selector) {
        $(tag).bind("keyup keydown focusout", function () {
            /* 제외하고 싶은 특수 기호를 추가하고 싶은 경우 /[ ]/ 안에 기입 하세요 */
            var special_pattern = "";
            if (selector == "onlyNumber") { //exposureCount, expense, mediaLabCommission, adAgencyCommission, salesAgencyCommission(ì«ì ì´ì¸ ëª¨ë  ë¬¸ì ìë ¥ ë¶ê°)
                special_pattern = /[^0-9]/g;
            } else if (selector == "search") { //treeSearch_category_tree
                special_pattern = /[%*?]/gi;
            }

            var temp = $(this).val();
            if (special_pattern.test(temp)) {
                $(this).val(temp.replace(special_pattern, ""));
            }
        });
        if (selector == "onlyNumber") { //exposureCount, expense, mediaLabCommission, adAgencyCommission, salesAgencyCommission(ì«ì ì´ì¸ ëª¨ë  ë¬¸ì ìë ¥ ë¶ê°)
            $(tag).css('imeMode', 'disabled').keypress(function (event) {
                if (event.which && (event.which < 48 || event.which > 57) && event.which != 8) {
                    event.preventDefault();
                }
            });
        }
    }

    function numberWithCommasAndOnlyNumber(selector) {
        $(selector).css('imeMode', 'disabled').keypress(function (event) {
            if (event.which && (event.which < 48 || event.which > 57)) {
                event.preventDefault();
            }
        }).bind("keyup keydown focusout", function () {
            var tmps = $(this).val().replace(/[^0-9]/g, '');
            var tmps2 = tmps.replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,");
            $(this).val(tmps2);
        });
    }

    //숫자 세자리마다 콤마찍는 함수
    function numberWithCommas(num) {
        if (num != null)
            return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        else
            return "-";
    }

    //앞글자만 대문자로 바꾸기
    function initCap(str) {
        var str = str.substring(0, 1).toUpperCase() + str.substring(1, str.length);
        return str;
    }



</script>

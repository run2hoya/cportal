require.config({
    baseUrl: '/cportalJS'
});

require(['common/ajaxUtil', 'common/utils', 'view/donationChance', 'view/pointView'],
    function (ajaxUtil, utils, donationChance, pointView) {

    let isOwner = false;
    let viewTitle;

    function init() {
        $('#contentBody').append(new EJS({url: '/cportalJS/view/ejs/bookingTable/bookingMainView.ejs'}).render());
        $('#registerModal').append(new EJS({url: '/cportalJS/view/ejs/bookingTable/bookingRegisterModal.ejs'}).render());
        if (feather) {feather.replace({width: 14, height: 14});}

        loadTable();
        addEvent();
    }

    function addEvent() {
        $('#clear').click(function () {
            $('.highlighted').removeClass('highlighted');
        });
        $('#home').click(function () {
            loadTable();
        });

        //let registerModal = new bootstrap.Modal($('#modalScrollable'));

        $('#registerBtn').click(function () {
            $('#registerModal').empty();
            $('#registerModal').append(new EJS({url: '/cportalJS/view/ejs/bookingTable/bookingRegisterModal.ejs'}).render());

            console.log(window.id);
            if(utils.isEmpty(window.id))
                $('#favorite').append(new EJS({url: '/cportalJS/view/ejs/bookingTable/notLogin.ejs'}).render());
            else
                $('#favorite').append(new EJS({url: '/cportalJS/view/ejs/bookingTable/favorite.ejs'}).render());

            $('#default-select-multi').select2({
                placeholder: '검색어를 입력하면 검색 할 수 있습니다.'
            });

            $('#bookingAddBtn').click(function () {
                var newOption = new Option($('#bookingAdd').val(), null, false, true);
                $('#default-select-multi').append(newOption).trigger('change');
            });


            $('#bookingDate').tagsinput({
                tagClass: 'badge bg-primary',
                itemValue: 'id',  // this will be used to set id of tag
                itemText: 'label' // this will be used to set text of tag
            });

            let selectCnt = 0;
            let targetDate;
            $('.highlighted').each(function (index, item) {
                $('#bookingDate').tagsinput('add', { id: $(item).attr('id'), label: $(item).attr('bookingTarget') });
                targetDate = $(item).attr('bookingTarget');
                selectCnt++;
            });

            if(selectCnt === 1) {
                $('#loopBtn').click(function () {
                    let split = targetDate.split('_');
                    let date = moment(split[0]);
                    while(true) {
                        date = date.add(7, "days");
                        console.log(date.format("YYYY-MM-DD") + '_' + split[1]);
                        let select = date.format("YYYY-MM-DD") + '_' + split[1];
                        let target = $("[bookingTarget=" + select.split(':').join('\\:') +"]");

                        if(target.length === 1) {
                            $('#bookingDate').tagsinput('add', { id: target.attr('id'), label: target.attr('bookingTarget') });
                        } else
                            break;

                    }
                });
            } else {
                $('#loopBtn').remove();
            }

            //test
            $('#bookmarkBtn1').click(function () {
                $('#bookingTitle').val('김태호');
                $('#bookingMemo').val('남길 메모를 입력합니다');
                $('#default-select-multi').val([1, 8, 9]).trigger('change');
            });

            $('#bookmarkBtn2').click(function () {
                $('#bookingTitle').val('외근');
                $('#bookingMemo').val('문자로 연락주세요');
                $('#default-select-multi').val([]).trigger('change');
            });
            $('#bookmarkBtn3').click(function () {
                $('#bookingTitle').val('휴가');
                $('#bookingMemo').val('문자로 연락주세요');
                $('#default-select-multi').val([]).trigger('change');
            });

            let modal = new bootstrap.Modal($('#modalScrollable'));
            $('#saveBtn').click(function () {
                registerBooking(modal);
            });

            modal.show();
        });
    }

    function donationChanceModal() {
        if($(".highlighted").length !== 1) {
            Swal.fire({title: 'ERROR', text: '하나만 선택 해주세요', icon: 'error'});
            return;
        }

        $('#donationChanceModalDiv').empty();
        $('#donationChanceModalDiv').append(new EJS({url: '/cportalJS/view/ejs/bookingTable/donationChanceModal.ejs'}).render());

        let ownerId, ownerName;

        $('.highlighted').each(function (index, item) {
            console.log($(item).attr('registerMember'));
            ownerId = $(item).attr('userId');
            ownerName = $(item).text();
            let member =  JSON.parse($(item).attr('registerMember'));

            for( let value of member ) {
                if(value.id === 'null')
                    $('#userDiv').append(new EJS({url: '/cportalJS/view/ejs/bookingTable/bookingNonMemberUser.ejs'}).render(value));
                else
                    $('#userDiv').append(new EJS({url: '/cportalJS/view/ejs/bookingTable/bookingUser.ejs'}).render(value));
            }
        });

        if(utils.isEmpty(ownerId)) {
            Swal.fire({title: 'INFO', text: '로그인 하지 않고 예약하였습니다. 오프라인으로 기부부탁증 발급하세요', icon: 'info'});
            return;
        }
        
        let donationModal = new bootstrap.Modal($('#donationChanceModal'));
        $('#issueBtn').click(function () {

            let plus = $('#pointPlus').val(), multiply = $('#pointMultiplication').val();

            let donationData = {};
            donationData.viewDonationList = [];

            $('.bookingUser').each(function (index, item) {
                console.log($(item).attr('id'));

                let id = $(item).attr('id');
                let viewDonation = {};
                viewDonation.title = viewTitle;
                viewDonation.des = $('#memo').val();
                viewDonation.viewMemberName = $('#' + id + '_name').text();
                viewDonation.viewMemberId = id;
                viewDonation.viewTableId = window.targetId;
                viewDonation.point = $('#' + id).val();
                viewDonation.ownerName = ownerName;
                viewDonation.plus = plus;
                viewDonation.multiply = multiply;
                viewDonation.ownerId = ownerId;
                viewDonation.donationState = 'PUBLISH';

                donationData.viewDonationList.push(viewDonation);
            });

            ajaxUtil.makeAjax("post", '/donation/new/', JSON.stringify(donationData), null).done(function(msg){
                console.log(msg);
                donationModal.hide();
                Swal.fire({title: 'success', text: '등록 요청에 성공하였습니다', icon: 'success'});
            }).
            fail(function(xhr, textStatus){
                donationModal.hide();
                console.log(xhr);
                console.log(textStatus);
                Swal.fire({title: 'ERROR', text: '관리자에게 연락 부탁 드립니다.', icon: 'error'});
            });

        });

        donationModal.show();
    }

    function registerBooking(modal) {

        if (utils.isEmpty($('#bookingDate').val())) {
            Swal.fire({title: 'ERROR', text: '예약일을 선택해 주세요', icon: 'error'});
            return false;
        }
        if (utils.isEmpty($("#bookingTitle").val())) {
            Swal.fire({title: 'ERROR', text: 'Title를 입력해주세요', icon: 'error'});
            return false;
        }

        let member = '<b>회원</b><ul>';
        let endMember = '</ul>'
        let nonmember = '<b>비회원</b><ul>';
        let endNonmember = '</ul>'

        let data = $("#default-select-multi").select2('data');
        let registerMemberList = [];
        for( let value of data ){
            console.log( value);
            let registerMember ={};
            registerMember.id = value.id;
            registerMember.text = value.text;
            if( value.id === 'null')
                nonmember += '<li>' + value.text + '</li>';
            else
                member += '<li>' + value.text + '</li>';

            registerMemberList.push(registerMember);
        }

        let bookingInfo = member + endMember + nonmember + endNonmember;
        if(!utils.isEmpty($('#bookingMemo').val())) {
            bookingInfo += '<hr><b>Memo</b><br>' + $('#bookingMemo').val();
        }

        let viewItem ={}, viewList =[];
        let jbSplit = $('#bookingDate').val().split(',');
        for ( let i in jbSplit ) {
            let view = {};
            view.title = $('#bookingTitle').val();
            view.id = jbSplit[i];
            view.memo = $('#bookingMemo').val();
            view.registerId = window.id;
            view.bookingInfo = bookingInfo;
            view.registerMember = JSON.stringify(registerMemberList);
            viewList.push(view);
        }
        viewItem.viewTableId = window.targetId;
        viewItem.viewList = viewList;

        ajaxUtil.makeAjax("put", '/view/booking/item/', JSON.stringify(viewItem), $('#modalScrollable')).done(function(msg){
            console.log(msg);
            modal.hide();
            loadTable();
            Swal.fire({title: 'success', text: '등록 요청에 성공하였습니다', icon: 'success'});

        }).
        fail(function(xhr, textStatus){
            modal.hide();
            console.log(xhr);
            console.log(textStatus);
            loadTable();
            Swal.fire({title: 'ERROR', text: '관리자에게 연락 부탁 드립니다.', icon: 'error'});

        });

    }

    function loadTable() {

        $('#contentMainDiv').empty();
        $('#contentMainDiv').append(new EJS({url: '/cportalJS/view/ejs/bookingTable/bookingTable.ejs'}).render());

        ajaxUtil.makeAjax("get", '/view/booking/' + window.targetId, null,null).done(function (msg) {

            $('#viewHead').append(msg.th);
            isOwner = msg.isOwner;
            viewTitle = msg.viewTitle;

            console.log(msg.isOwner);
            if(isOwner === true) {
                $('#donationChanceBtn').show();
                $('#donationViewBtn').show();
                $('#pointViewBtn').show();

                $("#donationChanceBtn").off().on('click', function() {donationChanceModal();});
                $("#donationViewBtn").off().on('click', function() {donationChance.donationChanceInit();});
                $("#pointViewBtn").off().on('click', function() {pointView.pointViewInit();});
            }

            for (let viewItem of msg.viewItemList) {
                let body = '<tr>';
                body += '<th>' + viewItem.title + '</th>';

                for (let view of viewItem.viewList) {
                    if(utils.isEmpty(view.title)) {
                        body += new EJS({url: '/cportalJS/view/ejs/bookingTable/itemNull.ejs'}).render({ id : view.id,
                            bookingTarget : view.viewDate + '_' + view.timezone});
                    } else {
                        let iclass;
                        if(view.bookingState === 'BOOKING' && view.registerId === parseInt(window.id))
                            iclass = 'my-select';
                        else if(view.bookingState === 'BOOKING')
                            iclass = 'booking';
                        else if(view.bookingState === 'CONFLICT')
                            iclass = 'booking-alert';

                        body += new EJS({url: '/cportalJS/view/ejs/bookingTable/item.ejs'}).render(
                            { id : view.id, itemClass : iclass, title : view.title,
                                bookingInfo : view.bookingInfo, bookingTarget : view.viewDate + '_' + view.timezone,
                                registerMember : view.registerMember, userId : view.registerId}
                        );
                    }
                }
                body += '</tr>';
                $('#viewBody').append(body);
            }

            var popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'));
            var popoverList = popoverTriggerList.map(function (popoverTriggerEl) {
                return new bootstrap.Popover(popoverTriggerEl);
            });

            createTable();

        }).fail(function (xhr, textStatus) {
            console.log(xhr);
            console.log(textStatus);
            Swal.fire({title: 'ERROR', text: '관리자에게 연락 부탁 드립니다.', icon: 'error'});
        });

    }

    function createTable() {

        var isMouseDown = false;
        $("#viewTable td")
            .mousedown(function () {
                isMouseDown = true;
                $(this).toggleClass("highlighted");
                return false; // prevent text selection
            })
            .mouseover(function () {
                if (isMouseDown) {
                    $(this).toggleClass("highlighted");
                }
            });

        $(document).mouseup(function () {
                isMouseDown = false;
            });

        $('table').each(function () {
            if ($(this).find('thead').length > 0 && $(this).find('th').length > 0) {
                // Clone <thead>
                var $w = $(window),
                    $t = $(this),
                    $thead = $t.find('thead').clone(),
                    $col = $t.find('thead, tbody').clone();

                // Add class, remove margins, reset width and wrap table
                $t
                    .addClass('sticky-enabled')
                    .css({
                        margin: 0,
                        width : '100%'
                    }).wrap('<div class="sticky-wrap" />');

                if ($t.hasClass('overflow-y')) $t.removeClass('overflow-y').parent().addClass('overflow-y');

                // Create new sticky table head (basic)
                $t.after('<table class="sticky-thead" />');

                // If <tbody> contains <th>, then we create sticky column and intersect (advanced)
                if ($t.find('tbody th').length > 0) {
                    $t.after('<table class="sticky-col" /><table class="sticky-intersect" />');
                }

                // Create shorthand for things
                var $stickyHead = $(this).siblings('.sticky-thead'),
                    $stickyCol = $(this).siblings('.sticky-col'),
                    $stickyInsct = $(this).siblings('.sticky-intersect'),
                    $stickyWrap = $(this).parent('.sticky-wrap');

                $stickyHead.append($thead);

                $stickyCol
                    .append($col)
                    .find('thead th:gt(0)').remove()
                    .end()
                    .find('tbody td').remove();

                $stickyInsct.html('<thead><tr><th>' + $t.find('thead th:first-child').html() + '</th></tr></thead>');

                // Set widths
                var setWidths = function () {
                        $t
                            .find('thead th').each(function (i) {
                            $stickyHead.find('th').eq(i).width($(this).width());
                        })
                            .end()
                            .find('tr').each(function (i) {
                            $stickyCol.find('tr').eq(i).height($(this).height());
                        });

                        // Set width of sticky table head
                        $stickyHead.width($t.width());

                        // Set width of sticky table col
                        $stickyCol.find('th').add($stickyInsct.find('th')).width($t.find('thead th').width())
                    },
                    repositionStickyHead = function () {
                        // Return value of calculated allowance
                        var allowance = calcAllowance();

                        // Check if wrapper parent is overflowing along the y-axis
                        if ($t.height() > $stickyWrap.height()) {
                            // If it is overflowing (advanced layout)
                            // Position sticky header based on wrapper scrollTop()
                            if ($stickyWrap.scrollTop() > 0) {
                                // When top of wrapping parent is out of view
                                $stickyHead.add($stickyInsct).css({
                                    opacity: 1,
                                    top    : $stickyWrap.scrollTop()
                                });
                            } else {
                                // When top of wrapping parent is in view
                                $stickyHead.add($stickyInsct).css({
                                    opacity: 0,
                                    top    : 0
                                });
                            }
                        } else {
                            // If it is not overflowing (basic layout)
                            // Position sticky header based on viewport scrollTop
                            if ($w.scrollTop() > $t.offset().top && $w.scrollTop() < $t.offset().top + $t.outerHeight() - allowance) {
                                // When top of viewport is in the table itself
                                $stickyHead.add($stickyInsct).css({
                                    opacity: 1,
                                    top    : $w.scrollTop() - $t.offset().top
                                });
                            } else {
                                // When top of viewport is above or below table
                                $stickyHead.add($stickyInsct).css({
                                    opacity: 0,
                                    top    : 0
                                });
                            }
                        }
                    },
                    repositionStickyCol = function () {
                        if ($stickyWrap.scrollLeft() > 0) {
                            // When left of wrapping parent is out of view
                            $stickyCol.add($stickyInsct).css({
                                opacity: 1,
                                left   : $stickyWrap.scrollLeft()
                            });
                        } else {
                            // When left of wrapping parent is in view
                            $stickyCol
                                .css({opacity: 0})
                                .add($stickyInsct).css({left: 0});
                        }
                    },
                    calcAllowance = function () {
                        var a = 0;
                        // Calculate allowance
                        $t.find('tbody tr:lt(3)').each(function () {
                            a += $(this).height();
                        });

                        // Set fail safe limit (last three row might be too tall)
                        // Set arbitrary limit at 0.25 of viewport height, or you can use an arbitrary pixel value
                        if (a > $w.height() * 0.25) {
                            a = $w.height() * 0.25;
                        }

                        // Add the height of sticky header
                        a += $stickyHead.height();
                        return a;
                    };

                setWidths();

                $t.parent('.sticky-wrap').scroll($.throttle(250, function () {
                    repositionStickyHead();
                    repositionStickyCol();
                }));

                $w
                    .load(setWidths)
                    .resize($.debounce(250, function () {
                        setWidths();
                        repositionStickyHead();
                        repositionStickyCol();
                    }))
                    .scroll($.throttle(250, repositionStickyHead));
            }
        });
    }

    init();
});

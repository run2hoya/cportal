require.config({
    baseUrl: '/cportalJS'
});

require([], function () {


    function init() {
        $('#contentBody').append(new EJS({url: '/cportalJS/view/ejs/viewTable.ejs'}).render());
        if (feather) {
            feather.replace({width: 14, height: 14});
        }

        initTable();
        createTable();
    }

    function initTable() {
        let haed = '<th>비춰보기</th>';
        let date = moment("2022-09-01");
        date.locale();
        for (let i = 0; i < 60; i++) {
            haed += '<th>' + date.format('MMMM Do (dd)') + '</th>';
            date.add(1, "days")
        }
        $('#viewHead').append(haed);

        let time = moment("2022-09-01 09:00");
        time.locale();

        for (let i = 0; i < 29; i++) {
            let body = '<tr>';
            body += '<th>' + time.format('HH:mm') + '</th>';
            for (let j = 0; j < 60; j++) {
                body += '<td class="able">김태호 </td>';
            }

            $('#viewBody').append(body);

            time.add(30, "m")
        }

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

        $(document)
            .mouseup(function () {
                isMouseDown = false;
            });

    }

    function createTable() {
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

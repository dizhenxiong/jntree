<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="title" content="百度脑图（KityMinder）">
    <meta name="keyword" content="kityminder,脑图,思维导图,kity,svg,minder,百度,fex,前端,在线">
    <meta name="description" content="百度脑图，便捷的脑图编辑工具。让您在线上直接创建、保存并分享你的思路。">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">

    <script src="/baidu/lib/jquery-2.1.0.min.js" charset="utf-8"></script>
    <script src="/baidu/lib/ZeroClipboard.min.js" charset="utf-8"></script>
    <script type="/baidu/text/javascript">
        ZeroClipboard.setDefaults( { moviePath: 'lib/ZeroClipboard.swf' } );
    </script>

    <script src="/baidu/kity/dist/kity.js" charset="utf-8"></script>
    <script src="/baidu/import.js" charset="utf-8"></script>
    <script src="/baidu/kityminder.config.js" charset="utf-8"></script>
    <script src="/baidu/lang/zh-cn/zh-cn.js" charset="utf-8"></script>

    <script src="/baidu/lib/zip.js" charset="utf-8"></script>
    <script>
        zip.inflateJSPath = 'lib/inflate.js';
    </script>

    <script src="/baidu/lib/jquery.xml2json.js" charset="utf-8"></script>


    <script src="/baidu/lib/baidu-frontia-js-full-1.0.0.js" charset="utf-8"></script>
    <script src="/baidu/src/util/httpUtil2.js" charset="utf-8"></script>
    <script src="/baidu/social/draftmanager.js" charset="utf-8"></script>

    <script src="/baidu/social/social.js" charset="utf-8"></script>

    <link href="/baidu/social/social.css" rel="stylesheet">

    <link href="/baidu/themes/default/css/import.css" type="text/css" rel="stylesheet" />
    <link href="/baidu/favicon.ico" type="image/x-icon" rel="shortcut icon">
    <link href="/baidu/favicon.ico" type="image/x-icon" rel="apple-touch-icon-precomposed">
    <input type="hidden" id="treeID" value="${id}"/>
</head>

<body>
<div id="content-wrapper">
    <div id="panel"></div>

    <div id="kityminder" onselectstart="return false"></div>

    <div id="share-dialog">
        <h3>URL分享：</h3>
        <p>
            <input id="share-url" type="url" value="http://naotu.baidu.com/?shareId=kcev3dd" />
            <button id="copy-share-url" data-clipboard-target="share-url" type="button">复制</button>
        </p>
        <h3>社交分享：</h3>
        <p id="share-platform" class="bdsharebuttonbox">
            <a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a>
            <a href="#" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a>
            <a href="#" class="bds_tqq" data-cmd="tqq" title="分享到腾讯微博"></a>
            <a href="#" class="bds_renren" data-cmd="renren" title="分享到人人网"></a>
            <a href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a>
        </p>
    </div>

    <div id="about">
        <svg id="km-cat" viewBox="0 0 1200 1200" width="32px" height="32px">
            <g id="cat-face">
                <path d="M1066.769,368.482L1119.5,80L830,131.611C760.552,97.29,682.35,77.999,599.641,77.999
                    c-82.424,0-160.371,19.161-229.641,53.26L81,81l50.769,289l0,0c-33.792,69.019-52.77,146.612-52.77,228.641
                    c0,287.542,233.099,520.642,520.642,520.642s520.642-233.099,520.642-520.642C1120.282,516.011,1101.028,437.88,1066.769,368.482z"
                        />
            </g>
            <g id="cat-eye">
                <path style="fill:#FFFFFF;" d="M920.255,371C794.746,371,693,472.746,693,598.255s101.746,227.255,227.255,227.255
                    s227.255-101.746,227.255-227.255S1045.765,371,920.255,371z M920,746c-80.081,0-145-64.919-145-145s64.919-145,145-145
                    s145,64.919,145,145S1000.081,746,920,746z"/>
                <path style="fill:#FFFFFF;" d="M276.255,371C150.746,371,49,472.746,49,598.255s101.746,227.255,227.255,227.255
                    s227.255-101.746,227.255-227.255S401.765,371,276.255,371z M276,745c-80.081,0-145-64.919-145-145s64.919-145,145-145
                    s145,64.919,145,145S356.081,745,276,745z"/>
            </g>
        </svg>
        KityMinder
        <a id="km-version"
           href="https://github.com/fex-team/kityminder/blob/dev/CHANGELOG.md"
           target="blank"
           tabindex="-1">
        </a>
        under
        <a href="https://raw.githubusercontent.com/fex-team/kityminder/dev/LICENSE"
           target="_blank"
           tabindex="-1">BSD License
        </a>.
        Powered by f-cube,
        <a href="http://fex.baidu.com"
           target="_blank"
           tabindex="-1">FEX
        </a> |
        <a href="https://github.com/fex-team/kityminder.git"
           target="_blank"
           tabindex="-1">Source
        </a>
        <a href="https://github.com/fex-team/kityminder/issues/new"
           target="_blank"
           tabindex="-1">Bug
        </a> |
        <a href="mailto:kity@baidu.com"
           target="_blank"
           tabindex="-1">Contact Us
        </a>
    </div>
</div>
</body>

<!--脑图启动代码-->
<script>
    // create km instance
    km = KM.getKityMinder('kityminder');

    // New Version Notify
    $(function() {

        var lastVersion = localStorage.lastKMVersion;
        $('#km-version').text( 'v' + KM.version );

        if (lastVersion != KM.version) {
            $( '#km-version' ).addClass( 'new-version' );
            localStorage.lastKMVersion = KM.version;
        }
    });

    km.on('unziperror', function(ev) {
        alert('文件解析错误，文件可能已损坏！');
    });

    km.on('parseerror', function(ev) {
        alert('文件解析错误，文件可能已损坏！');
    });

    km.on('unknownprotocal', function(ev) {
        alert('不支持的文件格式！');
    });

    document.body.ontouchmove = function(e) {
        //e.preventDefault();
    }
</script>

<!--社会分享代码-->
<script>window._bd_share_config={"common":{"bdSnsKey":{},"bdMini":"2","bdMiniList":[],"bdPic":"","bdStyle":"1","bdSize":"32"},"share":{}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];
</script>

<!--Baidu Tongji Code-->
<script type="text/javascript">
    if (document.domain == 'naotu.baidu.com') {
        var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
        document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F0703917f224067c887f3664479a03887' type='text/javascript'%3E%3C/script%3E"));
    }
</script>
</html>
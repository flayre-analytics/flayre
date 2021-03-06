(function () {
    if (window.navigator.doNotTrack === '1') {
        console.log('Flayre respects DNT');
        return;
    }
    if (window.navigator.userAgent.indexOf('Googlebot') !== -1
        || window.navigator.userAgent.indexOf('YandexBot') !== -1) {
        console.log('Ignoring events for bots');
        return;
    }
    const flayreDomain = document.currentScript.src.split('/').slice(0, 3).join('/');
    const app = document.currentScript.dataset.app;

    fetch(`${flayreDomain}/api/events`, {
        method: 'POST',
        headers: {
            'Content-type': 'application/json',
        },
        body: JSON.stringify({
            appId: app,
            date: new Date().toISOString(),
            platform: window.navigator.platform,
            locale: window.navigator.language,
            data: window.location.pathname,
            type: 'VIEW'
        })
    }).catch((err) => {
        console.error(err)
    })
})();
package lialh4.ldf;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

import android.content.Context;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Main implements IXposedHookLoadPackage {
    private static final String ANDROID = "android";
    private static final String PMSI = "com.android.server.pm.PackageManagerServiceImpl";
    private static final String VIFS = "verifyInstallFromShell";
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if(!ANDROID.equals(lpparam.packageName)&&!ANDROID.equals(lpparam.processName))
            return;
        XposedBridge.log("LDF is in package " + ANDROID);
        findAndHookMethod(PMSI, lpparam.classLoader, VIFS, Context.class, int.class, String.class, new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                XposedBridge.log("LDF bypassed a verify");
                return null;
            }
        });
        XposedBridge.log("LDF replaced " + VIFS);
    }
}

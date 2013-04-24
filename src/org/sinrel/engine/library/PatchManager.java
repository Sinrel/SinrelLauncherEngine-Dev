package org.sinrel.engine.library;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.NewExpr;

import org.sinrel.engine.util.VirtualZipModifer;

public final class PatchManager {

	protected static String class_joinserver = "ayh", class_checkserver = "iu";
	protected static String methodname_joinserver, methodsign_joinserver;
	protected static String methodname_checkserver, methodsign_checkserver;

	public static void patchWorkDir(final VirtualZipModifer virtualJar, final ClassPool pool, final String workDir) throws Exception {
		final String sanDir = workDir.replace('\\', '/').replace("\"", "\\\"");

		CtClass clazz = pool.get("net.minecraft.client.Minecraft");
		CtField[] fields = clazz.getDeclaredFields();
		int i = 0;
		for (CtField f : fields)
			if (f.getSignature().equals("Ljava/io/File;"))
				if (++i == 2) {
					final String fieldname = f.getName();
					clazz.instrument(new ExprEditor() {
						@Override
						public void edit(FieldAccess f) throws CannotCompileException {
							if (f.getFieldName() == fieldname && f.isWriter()) {
								f.replace("{" + fieldname + " = new java.io.File(\"" + sanDir + "\"); }");
							}
						}
					});
					break;
				}
		virtualJar.putClass(clazz);
	}

	public static void patchCheckJoinserver(final VirtualZipModifer virtualJar, final ClassPool classPool, final String version, final String joinserver, final String checkserver) throws Exception {
		CtClass dummy = classPool.makeClass("sle.Dummy");
		dummy.addMethod(CtMethod.make("public static String bob(){return null;}", dummy));

		if (class_joinserver != null) {
			CtClass netClientHandler = classPool.get(class_joinserver);
			CtMethod mtd = netClientHandler.getMethod(methodname_joinserver, methodsign_joinserver);
			mtd.instrument(new ExprEditor() {
				@Override
				public void edit(NewExpr e) throws CannotCompileException {
					if (e.getClassName().equals("java.net.URL")) {
						e.replace("{ $_ = new java.net.URL($1.indexOf(\'?\')!=-1 ? \"" + joinserver.replace("\"", "\\\"") + "\"+\"?mob=\"+sle.Dummy.bob()+\"&\"+$1.substring($1.indexOf(\'?\')+1) : $1); }");
					}
				}
			});
			virtualJar.putClass(netClientHandler);
		}

		if (class_checkserver != null) {
			CtClass threadLoginVerifer = classPool.get(class_checkserver);
			CtMethod mtd = threadLoginVerifer.getMethod(methodname_checkserver, methodsign_checkserver);
			mtd.instrument(new ExprEditor() {
				@Override
				public void edit(NewExpr e) throws CannotCompileException {
					if (e.getClassName().equals("java.net.URL")) {
						e.replace("{ $_ = new java.net.URL($1.indexOf(\'?\')!=-1 ? \"" + checkserver.replace("\"", "\\\"") + "\"+$1.substring($1.indexOf(\'?\')) : $1); }");
					}
				}
			});
			virtualJar.putClass(threadLoginVerifer);
		}
		dummy.detach();

		try {
			CtClass rel = classPool.get("cpw.mods.fml.relauncher.FMLRelauncher");
			CtMethod method = rel.getDeclaredMethod("relaunchApplet");

			classPool.importPackage("java.security.ProtectionDomain");
			classPool.importPackage("cpw.mods.fml.relauncher.ReflectionHelper");
			method.insertBefore("Object cont=ReflectionHelper.getPrivateValue(ReflectionHelper.getClass(getClass().getClassLoader(),new String[] {\"java.awt.Component\"}),$1,new String[]{\"parent\"});" + "Class launcher=ReflectionHelper.getClass(getClass().getClassLoader(),new String[]{\"net.minecraft.Launcher\"}); \n" + "ReflectionHelper.findMethod(launcher,null,new String[]{\"addExtra\"},new Class[]{ClassLoader.class,ProtectionDomain.class}).invoke(cont,new Object[]{classLoader,getClass().getProtectionDomain()}); \n");

			virtualJar.putClass(rel);
		} catch (NotFoundException ex) {}
	}

}

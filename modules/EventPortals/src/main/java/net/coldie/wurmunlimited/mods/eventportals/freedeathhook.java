package net.coldie.wurmunlimited.mods.eventportals;


import com.wurmonline.server.players.Player;
import javassist.CtClass;
import javassist.CtPrimitiveType;
import javassist.bytecode.Descriptor;
import org.gotti.wurmunlimited.modloader.classhooks.HookManager;
import org.gotti.wurmunlimited.modloader.classhooks.InvocationHandlerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
public class freedeathhook {
    static public void setUpDeathEffectInterception() {
        String descriptor = Descriptor.ofMethod(CtPrimitiveType.voidType, new CtClass[] {
            CtPrimitiveType.booleanType,
            CtPrimitiveType.intType,
            CtPrimitiveType.intType
        });
        HookManager.getInstance().registerHook("com.wurmonline.server.players.Player", "setDeathEffects", descriptor, new InvocationHandlerFactory()
        {
            @Override
            public InvocationHandler createInvocationHandler() {
                return new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        int x = (int)args[1];
                        int y = (int)args[2];
                        if (!((boolean) args[0]) && eventmod.checkfreedeath(x,y,((Player) proxy).getName())) {
                            args[0] = true;
                        }
                        return method.invoke(proxy, args);
                    }
                };
            }
        });
    }
}
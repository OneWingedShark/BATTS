package package_Ada_Language.ir_DIANA_2022;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.graalvm.nativeimage.impl.ConfigurationCondition;
import org.graalvm.nativeimage.impl.RuntimeSerializationSupport;

import package_Ada_Language.Ada_Node;

public abstract class DIANA_ROOT extends Ada_Node implements Serializable //, RuntimeSerializationSupport
{
	private static final long serialVersionUID = 3540148400684060066L;

	
/*
    void registerIncludingAssociatedClasses(ConfigurationCondition condition, Class<?> clazz);

    void register(ConfigurationCondition condition, Class<?>... classes);

    void registerWithTargetConstructorClass(ConfigurationCondition condition, Class<?> clazz, Class<?> customTargetConstructorClazz);

    void registerWithTargetConstructorClass(ConfigurationCondition condition, String className, String customTargetConstructorClassName);

    void registerLambdaCapturingClass(ConfigurationCondition condition, String lambdaCapturingClassName);

    default void registerLambdaCapturingClass(ConfigurationCondition condition, Class<?> lambdaCapturingClass) {
        registerLambdaCapturingClass(condition, lambdaCapturingClass.getName());
    }

    void registerProxyClass(ConfigurationCondition condition, List<String> implementedInterfaces);

    default void registerProxyClass(ConfigurationCondition condition, Class<?>... implementedInterfaces) {
        registerProxyClass(condition, Arrays.stream(implementedInterfaces).map(Class::getName).collect(Collectors.toList()));
    }
//*/	
}

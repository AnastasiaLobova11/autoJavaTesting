package com.project.testapplication.service;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectMethod;

public class RunJUnit5TestsFromJava {
    public SummaryGeneratingListener listener = new SummaryGeneratingListener();

    private  String methodName;
    private final String testClassName;

    public RunJUnit5TestsFromJava(String testClassName) {
        this.testClassName = testClassName;
    }

    /**
     * Работает только с одним тестовым методом
     **/
    public void runOne() {
        String s = testClassName + "#" + methodName
                + "(java.lang.reflect.Method,java.lang.Class)";
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(selectMethod(s))
                .build();
        Launcher launcher = LauncherFactory.create();
        TestPlan testPlan = launcher.discover(request);
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);
    }
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
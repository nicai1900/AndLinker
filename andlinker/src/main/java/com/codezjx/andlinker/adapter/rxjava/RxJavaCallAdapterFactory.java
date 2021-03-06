package com.codezjx.andlinker.adapter.rxjava;

import com.codezjx.andlinker.CallAdapter;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import rx.Observable;
import rx.Scheduler;


/**
 * A {@linkplain CallAdapter.Factory call adapter} which uses RxJava for creating observables.
 */
public class RxJavaCallAdapterFactory extends CallAdapter.Factory {

    private final Scheduler mScheduler;

    private RxJavaCallAdapterFactory(Scheduler scheduler) {
        mScheduler = scheduler;
    }

    public static RxJavaCallAdapterFactory create() {
        return new RxJavaCallAdapterFactory(null);
    }

    public static RxJavaCallAdapterFactory createWithScheduler(Scheduler scheduler) {
        if (scheduler == null) throw new NullPointerException("scheduler == null");
        return new RxJavaCallAdapterFactory(scheduler);
    }
    
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations) {
        Class<?> rawType = getRawType(returnType);
        if (rawType != Observable.class) {
            return null;
        }

        if (!(returnType instanceof ParameterizedType)) {
            throw new IllegalStateException("Observable return type must be parameterized"
                    + " as Observable<Foo> or Observable<? extends Foo>");
        }
        return new RxJavaCallAdapter(mScheduler);
    }
    
}

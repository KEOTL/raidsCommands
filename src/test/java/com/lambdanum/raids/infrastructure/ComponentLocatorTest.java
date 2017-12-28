package com.lambdanum.raids.infrastructure;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

import com.lambdanum.raids.infrastructure.injection.ComponentLocator;
import com.lambdanum.raids.infrastructure.injection.InjectionException;

import java.util.List;

import org.junit.Test;

public class ComponentLocatorTest {

    private TestableComponentLocator componentLocator = new TestableComponentLocator();

    public static class SomeClass {}

    public static class SomeDependentClass {
        public SomeClass someClass;

        public SomeDependentClass(SomeClass someClass) {
            this.someClass = someClass;
        }
    }

    public static class SomeChildClass extends SomeClass{
    }

    @Test(expected = InjectionException.class)
    public void givenComponentNotFound_whenGettingComponent_thenThrowInjectionException() {
        componentLocator.get(SomeClass.class);
    }

    @Test
    public void givenStoredClass_whenGettingComponent_thenReturnInstantiatedObject() {
        componentLocator.bind(SomeClass.class).to(SomeClass.class);

        SomeClass someClass = componentLocator.get(SomeClass.class);

        assertTrue(someClass != null);
        assertTrue(someClass instanceof SomeClass);
    }

    @Test
    public void givenStoredObject_whenGettingComponentForClass_thenReturnTheObjectAsASingleton() {
        SomeClass singleton = new SomeClass();
        componentLocator.bind(singleton).to(SomeClass.class);

        SomeClass someClass = componentLocator.get(SomeClass.class);

        assertEquals(singleton, someClass);
    }

    @Test
    public void givenClassWithDependencies_whenGettingComponent_thenDependenciesAreResolvedRecursively() {
        componentLocator.bind(SomeDependentClass.class).to(SomeDependentClass.class);
        componentLocator.bind(SomeClass.class).to(SomeClass.class);

        SomeDependentClass someDependentClass = componentLocator.get(SomeDependentClass.class);

        assertTrue(someDependentClass.someClass != null);
    }

    @Test
    public void whenGettingChildrenClasses_thenReturnAllChildrenFound() {
        componentLocator.bind(SomeChildClass.class).to(SomeChildClass.class);
        List<SomeClass> allChildren = componentLocator.getAllChildren(SomeClass.class);

        assertEquals("only one class should be found", 1, allChildren.size());
        assertTrue(allChildren.get(0) instanceof SomeChildClass);
    }

    @Test
    public void givenChildBoundToAbstraction_whenGettingAbstractClass_thenReturnChild() {
        componentLocator.bind(SomeChildClass.class).to(SomeClass.class);

        SomeClass result = componentLocator.get(SomeClass.class);

        assertTrue(result instanceof SomeChildClass);
    }

    private class TestableComponentLocator extends ComponentLocator {
        @Override
        public innerIntermediate bind(Object type) {
            return super.bind(type);
        }

    }

}

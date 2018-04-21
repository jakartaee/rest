package javax.ws.rs.core;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

import javax.ws.rs.ext.RuntimeDelegate;

public class EntityTagTest {


    @Before
    public void setUp() {
        RuntimeDelegate.setInstance(mock(RuntimeDelegate.class));
    }

    @After
    public void tearDown() throws Exception {
        RuntimeDelegate.setInstance(null);
    }

    @Test
    public void shouldBeEqualToTheSameInstance() {
        EntityTag entityTag = new EntityTag("value", true);

        assertThat(entityTag, equalTo(entityTag));
        assertThat(entityTag.hashCode(), equalTo(entityTag.hashCode()));
    }

    @Test
    public void shouldBeEqualsForSameFieldValues() {
        EntityTag entityTag = new EntityTag("value", true);
        EntityTag entityTagWithSameValues = new EntityTag("value", true);
        assertThat(entityTag, equalTo(entityTagWithSameValues));
        assertThat(entityTag.hashCode(), equalTo(entityTagWithSameValues.hashCode()));
    }

    @Test
    public void shouldNotBeEqualIfValueFieldDiffers() {
        EntityTag entityTag = new EntityTag("value", true);
        EntityTag entityTagWithDifferentValue = new EntityTag("differentValue", true);

        assertThat(entityTag, not(equalTo(entityTagWithDifferentValue)));
        assertThat(entityTag.hashCode(), not(equalTo(entityTagWithDifferentValue.hashCode())));
    }

    @Test
    public void shouldNotBeEqualIfWeekSettingDiffers() {
        EntityTag entityTag = new EntityTag("value", true);
        EntityTag entityTagWithDifferentWeakSetting = new EntityTag("value", false);

        assertThat(entityTag, not(equalTo(entityTagWithDifferentWeakSetting)));
        assertThat(entityTag.hashCode(), not(equalTo(entityTagWithDifferentWeakSetting.hashCode())));
    }

    @Test
    public void shouldNeverEqualsNull() {
        EntityTag entityTag = new EntityTag("value", true);

        assertThat(entityTag, not(equalTo(null)));
    }

    @Test
    public void shouldNotBeEqualToObjectFromDifferentClass() {
        EntityTag entityTag = new EntityTag("value", true);

        assertThat(entityTag, not(equalTo(new Object())));
    }
}

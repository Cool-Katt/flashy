package sofuni.flashy.config;

import com.microsoft.azure.cognitiveservices.search.imagesearch.BingImageSearchAPI;
import com.microsoft.azure.cognitiveservices.search.imagesearch.BingImageSearchManager;
import com.microsoft.azure.cognitiveservices.search.imagesearch.models.ImageObject;
import com.microsoft.azure.cognitiveservices.search.imagesearch.models.ImagesModel;
import org.springframework.stereotype.Component;

@Component
public class BingConfig
{
    private final String subscriptionKey = "f650449ec1e240108cae9c64efc20378";
    String searchTerm = "the moon is very far";

    public String findImageURL(String searchTerm)
    {
        final String subscriptionKey = "f650449ec1e240108cae9c64efc20378";
        BingImageSearchAPI client = BingImageSearchManager.authenticate(subscriptionKey);
        ImagesModel imageResults = client.bingImages().search().withQuery(searchTerm).withMarket("en-uk").execute();

        if (imageResults != null && imageResults.value().size() > 0)
        {
            ImageObject firstImageResult = imageResults.value().get(0);

            return firstImageResult.contentUrl();
        } else
        {
            return null;
        }
    }
}

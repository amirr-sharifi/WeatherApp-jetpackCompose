package af.amir.weathermvi.domain.usecase

import af.amir.weathermvi.domain.model.location.LocationInfo
import af.amir.weathermvi.domain.repository.LocationRepository
import javax.inject.Inject

class GetLocationFromDBUseCase @Inject constructor(
    private val repo : LocationRepository,
) {
    operator fun invoke(): LocationInfo? {
        return repo.getLocationFromDb()
    }
}
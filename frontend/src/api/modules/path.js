import ApiService from '@/api'

const PathService = {
  get(sourceId, targetId, type) {
    return ApiService.get(`/paths?source=${sourceId}&target=${targetId}&type=${type}`)
  }
}

export default PathService
